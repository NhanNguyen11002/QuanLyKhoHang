package org.example.quanlykhohang.controller;

import javafx.application.Platform;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailSMTP {
    public static void sendOTP(String emailTo, String otp) {
        String username = "utemyvietnam@gmail.com";
        String password = "xmxfhgajalgcxxtk";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Runnable emailTask = () -> {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
                message.setSubject("Yêu cầu thay đổi mật khẩu");
                message.setText("Xin chào !\n"
                        + "\n"
                        + "Ai đó đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn, nếu đây không phải là bạn, vui lòng bỏ qua email này.\n"
                        + "\n"
                        + "Sử dụng mã kích hoạt này để khôi phục mật khẩu của bạn: " + otp);
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        };

        new Thread(emailTask).start();
    }
}
