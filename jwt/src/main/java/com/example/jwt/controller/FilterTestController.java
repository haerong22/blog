package com.example.jwt.controller;

import com.example.jwt.jwt.JwtAuthorization;
import com.example.jwt.jwt.JwtContext;
import com.example.jwt.jwt.MemberInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class FilterTestController {

    @GetMapping("/token/none")
    public String none() {
        log.info("token none");
        return "success";
    }

    @GetMapping("/token/required")
    public String required() {
        log.info("token payload : {}", JwtContext.getMemberInfo());
        return "success";
    }

    @GetMapping("/token/optional")
    public String optional() {
        log.info("token payload : {}", JwtContext.getMemberInfo());
        return "success";
    }
}
