package by.nyurush.blog.controller;

import by.nyurush.blog.exception.ArticleNotFoundException;
import by.nyurush.blog.exception.CommentNotFoundException;
import by.nyurush.blog.exception.NoPermissionException;
import by.nyurush.blog.exception.TagNotFoundException;
import by.nyurush.blog.exception.user.RedisCodeNotFoundException;
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

    @ExceptionHandler({
            UserNotFoundException.class,
            UserRoleNotFoundException.class,
            RedisCodeNotFoundException.class,
            ArticleNotFoundException.class,
            CommentNotFoundException.class,
            TagNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistException.class, UserAlreadyIsActiveException.class})
    public ResponseEntity<?> handleAlreadyExistException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<?> handleNoPermissionException() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
