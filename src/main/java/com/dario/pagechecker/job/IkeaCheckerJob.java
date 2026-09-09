package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.ikea.IkeaChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IkeaCheckerJob {

    private final IkeaChecker ikeaChecker;
    private final Environment environment;

    @Scheduled(fixedDelayString = "${ikea-checker.refresh-interval.ms}")
    public void run() {
        if (!environment.getProperty("ikea-checker.job.active", Boolean.class, false)) {
            return;
        }
        ikeaChecker.check();
    }
}
