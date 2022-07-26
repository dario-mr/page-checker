package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckScheduler {

    private final CheckService checkService;

    @Scheduled(fixedDelayString = "${page.refresh-interval.milliseconds}")
    public void run() {
        try {
            checkService.check();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
