package com.dario.pagechecker.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShutdownService {

    private final ApplicationContext context;

    public void shutdown(int exitCode) {
        log.info("Shutting down application");
        var status = SpringApplication.exit(context, () -> exitCode);
        System.exit(status);
    }
}
