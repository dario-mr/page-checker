package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.html.HtmlChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "html-checker.job.active", havingValue = "true")
public class HtmlCheckerJob {

    private final HtmlChecker htmlChecker;

    @Scheduled(fixedDelayString = "${html-checker.refresh-interval.ms}")
    public void run() {
        htmlChecker.check();
    }
}
