package com.dario.pagechecker.job;

import com.dario.pagechecker.core.service.visa.VisaStatusChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisaCheckerJob {

  private final VisaStatusChecker visaStatusChecker;
  private final Environment environment;

  @Scheduled(fixedDelayString = "${visa-checker.refresh-interval.ms}")
  public void run() {
    if (!environment.getProperty("visa-checker.job.active", Boolean.class, false)) {
      return;
    }
    visaStatusChecker.checkStatus();
  }
}
