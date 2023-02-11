package com.example.api.feign;

import com.example.api.feign.config.CustomConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth-service", configuration = CustomConfig.class)
public interface AuthWithConfigFeignClient {

    @GetMapping("/test/success/header")
    String withHeader();
}
