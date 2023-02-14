package com.example.api.feign;

import com.example.api.dto.TestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthFeignClient {

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

    @GetMapping("/test/exception")
    String exception();

    @GetMapping("/test/timeout")
    String timeout();
}
