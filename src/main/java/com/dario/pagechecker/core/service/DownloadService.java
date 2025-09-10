package com.dario.pagechecker.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadService {

    private final EmailService emailService;
    private final ShutdownService shutdownService;

    public Document download(String url) {
        Document page = null;
        try {
            page = Jsoup.connect(url).get();
        } catch (Exception ex) {
            var errorMessage = format("Failed to download page [%s].%sError: %s", url, lineSeparator(), ex.getMessage());

            log.error(errorMessage, ex);
            emailService.send("Failed to download page", errorMessage);
            shutdownService.shutdown(1);
        }

        return page;
    }
}
