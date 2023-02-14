package com.example.api.feign.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class GlobalCustomConfig {

    @Bean
    public Retryer retryer(){
        // 기본값 : 0.1초 간격으로 1초에 한번씩 최대 5번 시도
        return new Retryer.Default(100L, SECONDS.toMillis(1L), 3);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}