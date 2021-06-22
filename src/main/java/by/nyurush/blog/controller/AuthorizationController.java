package by.nyurush.blog.controller;

import by.nyurush.blog.dto.user.ResetRequestDto;
import by.nyurush.blog.dto.user.UserDto;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.service.RedisService;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final RedisService redisService;
    private final ConversionService conversionService;


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
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
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


}
