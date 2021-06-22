package by.nyurush.blog.exception.user;

public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException() {
        super();
    }

    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
