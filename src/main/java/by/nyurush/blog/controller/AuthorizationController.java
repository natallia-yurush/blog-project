package by.nyurush.blog.controller;

import by.nyurush.blog.dto.UserDto;
import by.nyurush.blog.entity.User;
import by.nyurush.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final ConversionService conversionService;


    @PostMapping("/registration")
    public UserDto registration(@Validated @RequestBody UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        return userService.register(user);
    }

    @PostMapping("/confirm/{email}/{hash_code}")
    public ResponseEntity<?> confirmUser(@PathVariable("hash_code") String hashCode,
                                         @PathVariable("email") String email) {
        if (userService.confirmUser(email, hashCode)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
