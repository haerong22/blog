package com.example.api.feign.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
//        log.info("methodKey => {}", methodKey);
//        log.info("response => {}", response);
        switch (response.status()) {
            case 400:
            case 404:
                break;
            default:
                throw new RuntimeException("custom exception");
        }
        return null;
    }
}