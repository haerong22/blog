package com.example.email.controller;

import com.example.email.service.EmailSender;
import com.example.email.dto.EmailSenderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailSender emailSender;

    @PostMapping("/email")
    public String sendEmail(EmailSenderDto dto) {
        emailSender.send(dto);
        return "success";
    }
}
