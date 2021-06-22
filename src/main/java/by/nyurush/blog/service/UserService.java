package by.nyurush.blog.service;

import by.nyurush.blog.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    User register(User user);

    void confirmUser(String hashCode);

    void resetPassword(String email);

    void updatePassword(String code, String newPassword);




}
