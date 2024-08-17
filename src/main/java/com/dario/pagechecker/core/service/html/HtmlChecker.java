package com.dario.pagechecker.core.service.html;

import static java.lang.String.format;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

@Service
@Slf4j
@RequiredArgsConstructor
public class HtmlChecker {

    private final DownloadService downloadService;
    private final ParseService parseService;
    private final EmailService emailService;
    private final ShutdownService shutdownService;

    @Value("${html-checker.url}")
    private String url;

    public void check() {
        log.info("Checking page for desired attribute...");

        Document page = downloadService.download();
        if (parseService.hasAttribute(page)) {
            log.info("Attribute found, sending email...");
            emailService.send("Item available!", format("Item is available at [%s].", url));

            shutdownService.shutdown(0);
        }
    }
}

