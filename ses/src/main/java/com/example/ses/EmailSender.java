package com.example.ses;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender {

    private final String host = "email-smtp.ap-northeast-2.amazonaws.com";
    private final String user = "AKIAZSBM2FZQ7GVUGBGV";
    private final String password = "BLKX9YRgnGYLRqNKnqWlRGgBiQ0qZd9TXnq4Svy0Wpsb";

    public void send() {

        Session session = createProps();
        String text = "Hello world!";
        String subject = "Hello world!";

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("haerong22@gmail.com"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("haerong22@gmail.com"));
            msg.setSubject(subject, "utf-8");
            msg.setContent(text, "text/html; charset=utf-8");

            Transport transport = session.getTransport();
            transport.connect(host, user, password);
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Session createProps() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(props);
    }
}
