package com.dario.pagechecker.core.service.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DownloadService {

    @Value("${html-checker.url}")
    private String url;

    public Document download() throws IOException {
        return Jsoup.connect(url).get();
    }
}
