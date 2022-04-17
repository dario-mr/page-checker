package com.dario.pagechecker.core.service;

import com.dario.pagechecker.core.gateway.DownloadGateway;
import com.dario.pagechecker.util.ShutdownManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckService {

    private final DownloadGateway downloadGateway;
    private final ParseService parseService;
    private final EmailService emailService;
    private final ShutdownManager shutdownManager;

    @Value("${page.url}")
    private String url;

    public void check() {
        log.info("Checking page for desired attribute...");

        Document page = downloadGateway.download();
        if (parseService.hasAttribute(page)) {
            log.info("Attribute found, sending email");
            emailService.send("Ticket available!", format("Ticket at %s is available for purchase.", url));

            shutdownManager.shutdown(0);
        }
    }
}

