package by.nyurush.blog.controller;

import by.nyurush.blog.dto.user.AuthenticationRequestDto;
import by.nyurush.blog.dto.user.ResetRequestDto;
import by.nyurush.blog.dto.user.UserDto;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.exception.user.UserAlreadyIsActiveException;
import by.nyurush.blog.exception.user.UserNotFoundException;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import by.nyurush.blog.service.RedisService;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final RedisService redisService;
    private final ConversionService conversionService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/registration")
    public UserDto registration(@Validated @RequestBody UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        User registeredUser = userService.register(user);
        return conversionService.convert(registeredUser, UserDto.class);
    }

    @PostMapping("/confirm/{hash_code}")
    public void confirmUser(@PathVariable("hash_code") String hashCode) {
        userService.confirmUser(hashCode);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email) {
        userService.resetPassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody ResetRequestDto requestDto) {
        userService.updatePassword(requestDto.getCode(), requestDto.getPassword());
    }

    @GetMapping("/check_code")
    public ResponseEntity<?> checkCode(@RequestParam("code") String code) {
        if (redisService.isCodeExist(code)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, requestDto.getPassword())
            );
            User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);

            if (!user.isActive()) {
                log.warn("User {} already is active", email);
                throw new UserAlreadyIsActiveException("User " + email + " already is active");
            }

            String token = jwtTokenProvider.createToken(email, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


}
