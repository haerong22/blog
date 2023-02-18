package com.example.jwt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

    @Autowired LoginService loginService;

    @Test
    void login_test() {
        String token = loginService.login(RequestSteps.getDefaultLoginRequest());
        System.out.println("token = " + token);
    }
}