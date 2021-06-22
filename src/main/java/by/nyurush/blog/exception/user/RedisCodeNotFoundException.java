package by.nyurush.blog.exception.user;

public class RedisCodeNotFoundException extends RuntimeException {
    public RedisCodeNotFoundException() {
        super();
    }

    public RedisCodeNotFoundException(String message) {
        super(message);
    }
}
