package com.dario.pagechecker.job;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dario.pagechecker.core.service.html.HtmlChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.html-checker.active", havingValue = "true")
public class HtmlCheckerJob {

    private final HtmlChecker htmlChecker;

    @Scheduled(fixedDelayString = "${html-checker.refresh-interval.ms}")
    public void run() {
        try {
            htmlChecker.check();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
