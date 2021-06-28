package by.nyurush.blog.controller;

import by.nyurush.blog.exception.user.UserAlreadyExistException;
import by.nyurush.blog.exception.user.UserAlreadyIsActiveException;
import by.nyurush.blog.exception.user.UserNotFoundException;
import by.nyurush.blog.exception.user.UserRoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, UserRoleNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistException.class, UserAlreadyIsActiveException.class})
    public ResponseEntity<?> handle() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
