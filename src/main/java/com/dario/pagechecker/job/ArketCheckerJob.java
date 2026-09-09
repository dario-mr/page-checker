package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.arket.ArketPriceChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArketCheckerJob {

    private final ArketPriceChecker arketPriceChecker;
    private final Environment environment;

    @Scheduled(fixedDelayString = "${arket-checker.refresh-interval.ms}")
    public void run() {
        if (!environment.getProperty("arket-checker.job.active", Boolean.class, false)) {
            return;
        }
        arketPriceChecker.checkPrice();
    }
}
