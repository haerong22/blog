package com.example.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "test")
@RefreshScope
@Component
public class AppInfo {

    private String name;
    private String message;
}
