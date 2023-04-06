package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
