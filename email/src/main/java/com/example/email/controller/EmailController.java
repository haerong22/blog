package com.example.email.controller;

import com.example.email.service.EmailSender;
import com.example.email.dto.EmailSenderDto;
import com.example.email.service.SpringEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailSender emailSender;
    private final SpringEmailSender springEmailSender;

    @PostMapping("/email")
    public String sendEmail(EmailSenderDto dto) {
        emailSender.send(dto);
        return "success";
    }

    @PostMapping("/email/spring")
    public String sendEmailWithSpring(EmailSenderDto dto) {
        springEmailSender.send(dto);
        return "success";
    }
}
