package by.nyurush.blog.service.impl;

import by.nyurush.blog.dto.UserDto;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.exception.UserAlreadyExistException;
import by.nyurush.blog.exception.UserNotFoundException;
import by.nyurush.blog.repository.UserRepository;
import by.nyurush.blog.repository.UserRoleRepository;
import by.nyurush.blog.service.MailService;
import by.nyurush.blog.service.RedisService;
import by.nyurush.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@PropertySource("classpath:constants.properties")
public class UserServiceImpl implements UserService {


    //todo check if user exist (in registry, and findBy...)

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MailService mailService;

    @Value("${mail.confirm}")
    private String registerMessageType;

    @Override
    @Transactional
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("IN register - there is user with email: {}", user.getEmail());
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " is already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(userRoleRepository.findByName("ROLE_USER")));
        user.setIsActive(false);
        user.setCreatedAt(LocalDate.now());
        User registeredUser = userRepository.save(user);

        String activationCode = generateCode();
        redisService.addCode(user.getEmail(), activationCode);
        mailService.sendConfirmationEmail(user.getEmail(), activationCode);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    //todo maybe throw exception ??
    @Override
    public boolean confirmUser(String email, String hashCode) {
        User user = userRepository.findByEmail(email);
        if (redisService.isValidCode(email, hashCode) && !user.getIsActive()) {
            user.setIsActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            log.warn("IN resetPassword - there is no user with email: {}", email);
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        String codeToReset = generateCode();
        mailService.sendResetPasswordEmail(email, codeToReset);
        redisService.addCode(codeToReset, email);

        log.info("IN resetPassword - mail sent to user {}", email);
    }

    //todo boolean or throw ?
    @Override
    public boolean updatePassword(String code, String newPassword) {
        String email = redisService.getValue(code);
        if(email.isBlank()) {
            log.warn("IN updatePassword - there is no such code in redis: {}", code);
            throw new RuntimeException("Code " + code + " in redis not found");
        }
        if(redisService.isValidCode(code, email)) {
            User user = userRepository.findByEmail(email);
            if(user != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
                return true;
            } else {
                log.warn("IN updatePassword - there is no user with email {}", email);
                throw new UserNotFoundException("User with email " + email + " not found.");
            }
        }
        return false;
    }

    private String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email);
        log.info("IN findByUsername - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public User findById(Long id) {
        //todo existByEmail
        User userResult = userRepository.findById(id).orElse(null);

        if (userResult == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", userResult, userResult.getId());
        return userResult;
    }
}
