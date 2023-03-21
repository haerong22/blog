package com.example.api.service;

import com.example.api.dto.TestDto;
import com.example.api.feign.AuthFeignClient;
import com.example.api.feign.AuthWithConfigFeignClient;
import com.example.api.feign.AuthWithUrlFeignClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class FeignClientTest {

    @Autowired
    AuthFeignClient authFeignClient;

    @Autowired
    AuthWithConfigFeignClient authWithConfigFeignClient;

    @Autowired
    AuthWithUrlFeignClient authWithUrlFeignClient;

    @Test
    void ok() {
        String result = authFeignClient.ok();
        System.out.println("result = " + result);
    }

    @Test
    void withQueryString() {
        String result = authFeignClient.withQueryString(getTestDto());
        System.out.println("result = " + result);
    }

    @Test
    void withPathVariable() {
        String result = authFeignClient.withPathVariable("test");
        System.out.println("result = " + result);
    }

    @Test
    void withHeader() {
        String result = authFeignClient.withHeader("token");
        System.out.println("result = " + result);
    }

    @Test
    void withBody() {
        String result = authFeignClient.withBody(getTestDto());
        System.out.println("result = " + result);
    }

    @Test
    void exception() {
        String result = authFeignClient.exception();
        System.out.println("result = " + result);
    }

    @Test
    void timeout() {
        for (int i = 0; i < 20; i++) {
            String result = authFeignClient.timeout();
            System.out.println("result = " + result);
        }
    }


    @Test
    void customConfig() {
        String result = authWithConfigFeignClient.withHeader();
        System.out.println("result = " + result);
    }

    @Test
    void withUrl() {
        String result = authWithUrlFeignClient.ok();
        System.out.println("result = " + result);
    }

    private TestDto getTestDto() {
        TestDto testDto = new TestDto();
        testDto.setUsername("username");
        testDto.setPassword("password");
        return testDto;
    }
}
