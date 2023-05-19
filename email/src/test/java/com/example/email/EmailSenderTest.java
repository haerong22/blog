package com.example.email;

import com.example.email.dto.EmailSenderDto;
import com.example.email.service.EmailSender;
import com.example.email.service.SpringEmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSenderTest {

    @Autowired
    EmailSender emailSender;

    @Autowired
    SpringEmailSender springEmailSender;

    @Test
    void send() throws InterruptedException {
        emailSender.send(
                EmailSenderDto.builder()
                        .subject("Hello World!")
                        .toEmail("haerong22@gmail.com")
                        .content("Hello!!")
                        .template("default_template")
                        .build()
        );

        Thread.sleep(3000);
    }

    @Test
    void send_with_spring() throws InterruptedException {
        springEmailSender.send(
                EmailSenderDto.builder()
                        .subject("Hello World!")
                        .toEmail("haerong22@gmail.com")
                        .content("Hello!!")
                        .template("default_template")
                        .build()
        );
        Thread.sleep(3000);
    }

}