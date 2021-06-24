package by.nyurush.blog.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super();
    }

    public TagNotFoundException(String message) {
        super(message);
    }
}