package com.example.upload.tus.config;

import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class TusConfig {

    @Value("${tus.data.path}")
    private String tusDataPath;

    @Value("${tus.data.expiration}")
    Long tusDataExpiration;

    @Bean
    public TusFileUploadService tus() {
        return new TusFileUploadService()
                .withStoragePath(tusDataPath)
                .withDownloadFeature()
                .withUploadExpirationPeriod(tusDataExpiration)
                .withThreadLocalCache(true)
                .withMaxUploadSize(1024L * 1024L * 1024L * 3)
                .withUploadURI("/tus/upload");
    }
}

