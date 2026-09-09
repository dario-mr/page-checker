package com.dario.pagechecker.core.service.visa;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "visa-checker")
public record VisaProperties(
    String url,
    boolean headless,
    Form form
) {

  public record Form(
      String referenceNumber,
      String additionalSuffix,
      String proceedingsType,
      String year) {

  }
}
