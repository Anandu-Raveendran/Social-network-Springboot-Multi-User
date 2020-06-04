package artshop.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class EmailClientImpl implements EmailClient {

    private JavaMailSender javaMailSender;
    private static final Logger LOG = LoggerFactory.getLogger(EmailClientImpl.class);

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String text) {
        sendEmail(toEmail, subject, text, fromEmail);
    }

    @Override
    public void sendEmail(String toEmail, String subject, String text, String from) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setFrom("47anandu007@gmail.com");
            mailMessage.setSubject(subject);
            mailMessage.setText(text);
            javaMailSender.send(mailMessage);
            LOG.info("Email sent to {} about {} on {}", toEmail, subject + " : " + text, new Timestamp(System.currentTimeMillis()));
        } catch (MailException exception) {
            LOG.error("Error while sending mail. Check emailId " + exception);
        }
    }

}
