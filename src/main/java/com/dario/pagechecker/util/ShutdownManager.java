package com.dario.pagechecker.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShutdownManager {

    private final ApplicationContext context;

    public void shutdown(int exitCode) {
        log.info("Shutting down application");
        SpringApplication.exit(context, () -> exitCode);
    }
}
