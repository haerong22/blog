package com.example.auth.controller;

import com.example.auth.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AppController {

    @GetMapping("/health_check")
    public String healthCheck() {
        return "Auth Service is available!";
    }

    @GetMapping("/test/success")
    public String ok() {
        log.info("ok");
        return "ok";
    }

    @GetMapping("/test/success/param")
    public String withQueryString(TestDto request) {
        log.info("query string ==> {}", request);
        return "withQueryString";
    }

    @GetMapping("/test/success/{value}")
    public String withPathVariable(@PathVariable String value) {
        log.info("path variable ==> {}", value);
        return "withPathVariable";
    }

    @GetMapping("/test/success/header")
    public String withHeader(@RequestHeader String token) throws InterruptedException {
        log.info("header ==> {}", token);
        Thread.sleep(5000L);
//        throw new RuntimeException();
        return "withHeader";
    }

    @PostMapping("/test/success")
    public String withBody(@RequestBody TestDto dto) {
        log.info("request ==> {}", dto);
        return "withBody";
    }

    @GetMapping("/test/exception")
    public void exception() {
        throw new RuntimeException("error");
    }

    @GetMapping("/test/timeout")
    public String timeout() throws InterruptedException {
        Thread.sleep(5000L);
        return "timeout";
    }
}
