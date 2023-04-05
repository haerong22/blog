package com.example.api.service;

import com.example.api.dto.LoginRequest;
import com.example.api.feign.AuthFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthFeignClient authFeignClient;

    public String login(LoginRequest request) {
        boolean result = authFeignClient.login(request);

        if (result) {
            log.info("login success!!");
            return "success";
        }

        log.info("login failed!!");
        return "failed";
    }
}
