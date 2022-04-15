package com.dario.pagechecker.core.service;

import com.dario.pagechecker.core.gateway.DownloadGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckService {

    private final DownloadGateway downloadGateway;
    private final ParseService parseService;
    private final EmailService emailService;
    private final ShutdownService shutdownService;

    public void check() {
        log.info("Checking page for desired attribute...");

        Document page = downloadGateway.download();
        if (parseService.hasAttribute(page)) {
            log.info("Attribute found, sending email");
            emailService.send();

            log.info("Shutting down application");
            shutdownService.shutdown(0);
        }
    }
}

