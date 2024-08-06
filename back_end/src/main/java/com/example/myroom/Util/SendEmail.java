package com.example.myroom.Util;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {


    @Value("${mail.smtp.host}")
    private String host_;


    @Value("${mail.smtp.port}")
    private String port_;


    @Value("${mail.smtp.email}")
    private String email_;


    @Value("${mail.smtp.pass}")
    private String pass_;


    private static String host;
    private static String pass;
    private static String email;
    private static String port;


    @PostConstruct
    public void init() {
        host = host_;
        port = port_;
        email = email_;
        pass = pass_;
    }

    public static String send(String to,String subject, String content){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(email, pass);

            }

        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(new InternetAddress("mar.heaven121020@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=UTF-8");
            System.out.println("sending...");
            Transport.send(message);
            return "OK";
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        return "FAIL";
    }
}
