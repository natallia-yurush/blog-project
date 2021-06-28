package by.nyurush.blog.service;

public interface MailService {

    void sendConfirmationEmail(String email, String code);

    void sendResetPasswordEmail(String email, String code);

}
