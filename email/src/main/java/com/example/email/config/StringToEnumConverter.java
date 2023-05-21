package com.example.email.config;

import com.example.email.service.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToEnumConverter {

    public static class ContentTypeConverter implements Converter<String, ContentType> {
        @Override
        public ContentType convert(String source) {
            return ContentType.valueOf(source.toUpperCase());
        }
    }
}

