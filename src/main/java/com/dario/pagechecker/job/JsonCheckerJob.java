package com.dario.pagechecker.job;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dario.pagechecker.core.service.json.JsonChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "json-checker.job.active", havingValue = "true")
public class JsonCheckerJob {

    private final JsonChecker jsonChecker;

    @Scheduled(fixedDelayString = "${json-checker.refresh-interval.ms}")
    public void run() {
        try {
            jsonChecker.check();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
