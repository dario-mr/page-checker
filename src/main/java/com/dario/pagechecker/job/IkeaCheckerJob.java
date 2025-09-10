package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.ikea.IkeaChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "ikea-checker.job.active", havingValue = "true")
public class IkeaCheckerJob {

    private final IkeaChecker ikeaChecker;

    @Scheduled(fixedDelayString = "${ikea-checker.refresh-interval.ms}")
    public void run() {
        ikeaChecker.check();
    }
}
