package com.dario.pagechecker.core.service.html;

import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlChecker {

    private final DownloadService downloadService;
    private final ParseService parseService;
    private final EmailService emailService;
    private final ShutdownService shutdownService;

    @Value("${html-checker.url}")
    private String url;

    public void check() {
        log.info("Checking page [{}] for desired attribute...", url);

        Document page = null;
        try {
            page = downloadService.download();
        } catch (Exception ex) {
            var errorMessage = format("Failed to download page [%s].%sError: %s", url, lineSeparator(), ex.getMessage());

            log.error(errorMessage, ex);
            emailService.send("Failed to download page", errorMessage);
            shutdownService.shutdown(1);
        }

        if (parseService.hasAttribute(page)) {
            log.info("Attribute found! Sending email...");
            emailService.send("Item available!", format("Item is available at [%s].", url));
            shutdownService.shutdown(0);
        }
    }
}

