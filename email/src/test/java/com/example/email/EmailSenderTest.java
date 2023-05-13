package com.example.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSenderTest {

    @Autowired EmailSender emailSender;

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

}