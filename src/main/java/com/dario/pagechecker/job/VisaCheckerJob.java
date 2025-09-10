package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.visa.VisaStatusChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "visa-checker.job.active", havingValue = "true")
public class VisaCheckerJob {

  private final VisaStatusChecker visaStatusChecker;

  @Scheduled(fixedDelayString = "${visa-checker.refresh-interval.ms}")
  public void run() {
    visaStatusChecker.checkStatus();
  }
}
