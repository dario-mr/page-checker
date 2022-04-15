package com.dario.pagechecker.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShutdownService {

    private final ApplicationContext context;

    public void shutdown(int exitCode) {
        SpringApplication.exit(context, () -> exitCode);
    }
}
