package com.dario.pagechecker.core.service.json;

import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import com.dario.pagechecker.proxy.ikea.dto.IkeaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonChecker {

    private final EmailService emailService;
    private final ShutdownService shutdownService;
    private final IkeaGateway ikeaGateway;

    @Value("${json-checker.url}")
    private String url;

    @Value("${json-checker.api-key}")
    private String ikeaApiKey;

    public void check() {
        log.info("Checking item availability at [{}]", url);

        IkeaResponse ikeaResponse = null;
        try {
            ikeaResponse = ikeaGateway.get(url, ikeaApiKey);
        } catch (Exception ex) {
            var errorMessage = format("URL: [%s]%sError: %s", url, lineSeparator(), ex.getMessage());

            log.error(errorMessage, ex);
            emailService.send("Error getting item", errorMessage);
            shutdownService.shutdown(1);
        }

        var isInStock = ikeaResponse.data().stream()
                .filter(data -> data.availableStocks() != null)
                .flatMap(data -> data.availableStocks().stream())
                .flatMap(stock -> stock.probabilities().stream())
                .anyMatch(probability -> !"OUT_OF_STOCK".equals(probability.communication().messageType()));

        if (isInStock) {
            log.info("Item available! Sending email...");
            emailService.send("Item available!", "Item is available");
            shutdownService.shutdown(0);
        }
    }
}

