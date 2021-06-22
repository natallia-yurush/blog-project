package by.nyurush.blog.service;

import by.nyurush.blog.entity.User;

public interface UserService {

    User findByEmail(String email);

    User register(User user);

    void confirmUser(String hashCode);

    void resetPassword(String email);

    void updatePassword(String code, String newPassword);

}
