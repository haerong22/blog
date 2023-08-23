package com.example.ses;

import org.junit.jupiter.api.Test;

class EmailSenderTest {

    EmailSender emailSender = new EmailSender();

    @Test
    void sendTest() {
        emailSender.send();
    }

}