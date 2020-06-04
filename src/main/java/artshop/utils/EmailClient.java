package artshop.utils;

public interface EmailClient {
    void sendEmail(String toEmail, String subject, String text);

    void sendEmail(String toEmail, String subject, String text, String from);
}
