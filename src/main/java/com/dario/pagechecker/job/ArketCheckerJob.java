package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.arket.ArketPriceChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "arket-checker.job.active", havingValue = "true")
public class ArketCheckerJob {

    private final ArketPriceChecker arketPriceChecker;

    @Scheduled(fixedDelayString = "${arket-checker.refresh-interval.ms}")
    public void run() {
        arketPriceChecker.checkPrice();
    }
}
