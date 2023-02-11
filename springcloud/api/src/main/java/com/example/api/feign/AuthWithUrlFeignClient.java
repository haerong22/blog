package com.example.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gateway", url = "${gateway.url}")
public interface AuthWithUrlFeignClient {

    @GetMapping("/auth/test/success")
    String ok();
}
