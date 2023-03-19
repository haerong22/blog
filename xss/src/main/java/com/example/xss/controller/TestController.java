package com.example.xss.controller;

import com.example.xss.dto.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public TestResponse test(String text) {
        return new TestResponse(text);
    }
}
