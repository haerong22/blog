package com.example.jwt.controller;

import com.example.jwt.jwt.JwtAuthorization;
import com.example.jwt.jwt.MemberInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/token/none")
    public String none() {
        log.info("token none");
        return "success";
    }

    @GetMapping("/token/required")
    public String required(
            @JwtAuthorization MemberInfo memberInfo
    ) {
        log.info("token payload : {}", memberInfo);
        return "success";
    }

    @GetMapping("/token/optional")
    public String optional(
            @JwtAuthorization(required = false) MemberInfo memberInfo
    ) {
        log.info("token payload : {}", memberInfo);
        return "success";
    }
}
