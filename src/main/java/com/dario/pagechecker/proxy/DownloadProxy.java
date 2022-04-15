package com.dario.pagechecker.proxy;

import com.dario.pagechecker.core.gateway.DownloadGateway;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class DownloadProxy implements DownloadGateway {

    @Value("${page.url}")
    private String url;

    public Document download() {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception ex) {
            throw new RuntimeException(format("Failed to download page %s", url), ex);
        }
    }
}
