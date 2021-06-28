package by.nyurush.blog.service.impl;

import by.nyurush.blog.entity.User;
import by.nyurush.blog.service.MailService;
import by.nyurush.blog.service.RedisService;
import by.nyurush.blog.exception.user.RedisCodeNotFoundException;
import by.nyurush.blog.exception.user.UserAlreadyExistException;
import by.nyurush.blog.exception.user.UserAlreadyIsActiveException;
import by.nyurush.blog.exception.user.UserNotFoundException;
import by.nyurush.blog.repository.UserRepository;
import by.nyurush.blog.repository.UserRoleRepository;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:constants.properties")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final MailService mailService;

    @Override
    @Transactional
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("IN register - there is no user with email: {}", user.getEmail());
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " is already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(userRoleRepository.findByName("ROLE_USER").orElseThrow()));
        user.setActive(false);
        user.setCreatedAt(LocalDate.now());
        User registeredUser = userRepository.save(user);

        String activationCode = generateCode();
        redisService.addCode(activationCode, user.getEmail());
        mailService.sendConfirmationEmail(user.getEmail(), activationCode);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public void confirmUser(String hashCode) {
        if (redisService.isCodeExist(hashCode)) {
            String email = redisService.getValue(hashCode);
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

            if (!user.isActive()) {
                user.setActive(true);
                userRepository.save(user);
            } else {
                log.warn("IN confirmUser - user with email {} is already active", email);
                throw new UserAlreadyIsActiveException();
            }
        } else {
            log.warn("IN confirmUser - failed to confirm user: hash code {} not found.", hashCode);
            throw new RedisCodeNotFoundException("Failed to confirm user: hash code not found.");
        }
    }

    @Override
    public void resetPassword(String email) {
        userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        String codeToReset = generateCode();
        mailService.sendResetPasswordEmail(email, codeToReset);
        redisService.addCode(codeToReset, email);

        log.info("IN resetPassword - mail sent to user {}", email);
    }

    @Override
    public void updatePassword(String code, String newPassword) {
        String email = redisService.getValue(code);
        if (email == null || email.isBlank()) {
            log.warn("IN updatePassword - there is no such code in redis: {}", code);
            throw new RuntimeException("Code " + code + " in redis not found");
        }
        if (redisService.isValidCode(code, email)) {
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            user.setPassword(passwordEncoder.encode(newPassword));

        } else {
            log.warn("IN updatePassword - there is no code in redis {}", code);
            throw new RedisCodeNotFoundException();
        }
    }

    private String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) {
        User foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        log.info("IN findByUsername - user found com email: {}", email);
        return foundUser;
    }

}
