package com.example.jwt.service;

import com.example.jwt.dto.LoginRequest;

public class RequestSteps {

    public static LoginRequest getDefaultLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bobby");
        loginRequest.setPassword("1234");
        return loginRequest;
    }
}
