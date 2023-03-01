package com.example.api.controller;

import com.example.api.config.AppInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppInfo appInfo;

    @GetMapping("/health_check")
    public String healthCheck() {
        log.info("AppInfo: {}", appInfo);
        return "Api Service is available!";
    }
}
