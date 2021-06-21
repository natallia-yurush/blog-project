package by.nyurush.blog.service;

public interface MailService {
 //   void sendEmail(String email, String code, String messageType);

    //todo ????
    void sendConfirmationEmail(String email, String code);
    void sendResetPasswordEmail(String email, String code);



}
