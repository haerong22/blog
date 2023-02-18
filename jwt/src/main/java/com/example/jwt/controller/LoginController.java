package com.example.jwt.controller;

import com.example.jwt.dto.LoginRequest;
import com.example.jwt.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    private String login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
