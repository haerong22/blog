package com.example.api.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class TestServiceTest {

    @Autowired TestService testService;

    @Test
    void circuit_breaker_test() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            testService.call();
        }
    }

    @Test
    void retry_test() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            testService.retry();
        }
    }

    @Test
    void both_test() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            testService.retryCall();
        }
    }
}