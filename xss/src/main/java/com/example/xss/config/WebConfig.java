package com.example.xss.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final HtmlCharacterEscapes htmlCharacterEscapes;

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        log.info("MappingJackson2HttpMessageConverter init");
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(htmlCharacterEscapes);
        return new MappingJackson2HttpMessageConverter(copy);
    }
}
