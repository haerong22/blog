package com.example.api;

import com.example.api.config.AppInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppStart implements ApplicationListener<ApplicationStartedEvent> {

    private final AppInfo appInfo;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("==============================================================");
        log.info("AppInfo: {}", appInfo);
        log.info("==============================================================");
    }
}
