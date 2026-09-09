package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.html.HtmlChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HtmlCheckerJob {

    private final HtmlChecker htmlChecker;
    private final Environment environment;

    @Scheduled(fixedDelayString = "${html-checker.refresh-interval.ms}")
    public void run() {
        if (!environment.getProperty("html-checker.job.active", Boolean.class, false)) {
            return;
        }
        htmlChecker.check();
    }
}
