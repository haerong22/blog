package com.example.api.feign.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class CustomConfig {

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${token}") String token) {
        return requestTemplate -> requestTemplate.header("token", token);
    }
}