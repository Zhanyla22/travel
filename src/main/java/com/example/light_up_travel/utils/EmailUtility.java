package com.example.light_up_travel.utils;

import com.example.light_up_travel.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@UtilityClass
public class EmailUtility {

    private final String siteURL = "https://light-up-travel.herokuapp.com/";
    public void sendVerificationEmail(User user, JavaMailSender mailSender)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "trevelproject.kg@gmail.com";
        String senderName = "Light Up Travel";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Light Up Travel.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "api/auth/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);

        System.out.println(content);

    }

    public static void sendPasswordResetCode(String token, User user, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "trevelproject.kg@gmail.com";
        String senderName = "Light Up Travel";
        String subject = "Password Reset";
        String content = "Dear [[name]],<br>"
                + "Password reset code:<br>"
                + token +"<br>"
                + "Thank you,<br>"
                + "Light Up Travel.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());

        helper.setText(content, true);
        mailSender.send(message);

        System.out.println(content);
    }
    public void sendLoginAndPassword(String email, String password, JavaMailSender javaMailSender)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("trevelproject.kg@gmail.com", "Light Up Travel");
        helper.setTo(email);

        String subject = "User password";

        String content = "<p>Hello, dear user! </p>"
                + "<p>Your registration is done successfully.</p>"
                + "<p> These are your login information;</p>"
                + "<p>Email: <strong>" + email + "</strong></p>"
                + "<p>Password: <strong>" + password + "</strong></p>"
                + "<p>Please change your password after logging in!"
                + "<p>Enjoy our website and have a nice day! :))";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}