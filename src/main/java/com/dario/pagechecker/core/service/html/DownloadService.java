package com.dario.pagechecker.core.service.html;

import static java.lang.String.format;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Service
public class DownloadService {

    @Value("${html-checker.url}")
    private String url;

    public Document download() {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception ex) {
            throw new RuntimeException(format("Failed to download page %s", url), ex);
        }
    }
}
