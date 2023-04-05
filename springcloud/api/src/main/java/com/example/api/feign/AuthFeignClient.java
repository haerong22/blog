package com.example.api.feign;

import com.example.api.dto.LoginRequest;
import com.example.api.dto.TestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthFeignClient {

    @PostMapping("/login")
    boolean login(@RequestBody LoginRequest request);

    @GetMapping("/test/success")
    String ok();

    @GetMapping("/test/success/param")
    String withQueryString(@RequestParam TestDto request);

    @GetMapping("/test/success/{value}")
    String withPathVariable(@PathVariable String value);

    @GetMapping("/test/success/header")
    String withHeader(@RequestHeader String token);

    @PostMapping("/test/success")
    String withBody(@RequestBody TestDto dto);

    @Retry(name = "customRetry", fallbackMethod = "retryFallback")
    @GetMapping("/test/exception")
    String exception();

    default String retryFallback(Exception e) {
        return "retryFallback";
    }

    @CircuitBreaker(name = "customCircuitBreaker", fallbackMethod = "timeoutFallback")
    @GetMapping("/test/timeout")
    String timeout();

    default String timeoutFallback(Throwable e) {
        return "[AuthFeignClient] timeoutFallback";
    }
}
