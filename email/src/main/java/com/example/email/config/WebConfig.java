package com.example.email.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @SneakyThrows
    @Override
    public void addFormatters(FormatterRegistry registry) {
        for ( Class<?> c : StringToEnumConverter.class.getClasses()) {
            registry.addConverter((Converter<?, ?>) c.getConstructor().newInstance());
        }
    }
}