package com.example.api.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    @CircuitBreaker(name = "customCircuitBreaker", fallbackMethod = "callFallback")
    public String call() throws InterruptedException {

        long before = System.currentTimeMillis();
        Thread.sleep(5000L);
        long after = System.currentTimeMillis();
        log.info("[TestService] call => {}ms", after - before);

        return "success";
    }

    private String callFallback(Exception e) {
        log.info("[TestService] callFallback");
        return "fallback";
    }

    @CircuitBreaker(name = "customCircuitBreaker", fallbackMethod = "callFallback")
    @Retry(name = "customRetry", fallbackMethod = "retryFallback")
    public String retry() throws InterruptedException {
        log.info("[TestService] retry");
        throw new RuntimeException();
    }

    @Retry(name = "customRetry", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "customCircuitBreaker", fallbackMethod = "circuitBreakerFallback")
    public String retryCall() throws InterruptedException {
        long before = System.currentTimeMillis();
        Thread.sleep(5000L);
        long after = System.currentTimeMillis();
        log.info("[TestService] retryCall => {}ms", after - before);
        throw new RuntimeException();
    }

    private String retryFallback(Exception e) {
        log.info("[TestService] retryFallback");
        return "retryFallback";
    }

    private String circuitBreakerFallback(Exception e) {
        log.info("[TestService] circuitBreakerFallback");
        return "circuitBreakerFallback";
    }

}
