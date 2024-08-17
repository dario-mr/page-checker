package com.dario.pagechecker.core.service.json;

import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import com.dario.pagechecker.dto.IkeaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonChecker {

    private final EmailService emailService;
    private final ShutdownService shutdownService;
    private final RestService restService;

    @Value("${json-checker.url}")
    private String url;

    public void check() {
        log.info("Checking URL [{}] for desired attribute...", url);

        IkeaResponse ikeaResponse = null;
        try {
            ikeaResponse = restService.get(url);
        } catch (RestClientException ex) {
            emailService.send("Error loading item URL", format("URL: [%s]%sError: %s", url, lineSeparator(), ex.getMessage()));
            shutdownService.shutdown(0);
        }

        var isInStock = ikeaResponse.data().stream()
                .filter(data -> data.availableStocks() != null && !data.availableStocks().isEmpty())
                .map(data -> data.availableStocks().get(0))
                .filter(stock -> !stock.probabilities().isEmpty())
                .map(stock -> stock.probabilities().get(0))
                .anyMatch(probability -> !probability.communication().messageType().equals("OUT_OF_STOCK"));

        if (isInStock) {
            log.info("Item available! Sending email...");
            emailService.send("Item available!", "Item is available");
            shutdownService.shutdown(0);
        }
    }
}

