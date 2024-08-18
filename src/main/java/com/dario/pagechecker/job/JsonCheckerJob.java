package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.json.JsonChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "json-checker.job.active", havingValue = "true")
public class JsonCheckerJob {

    private final JsonChecker jsonChecker;

    @Scheduled(fixedDelayString = "${json-checker.refresh-interval.ms}")
    public void run() {
        jsonChecker.check();
    }
}
