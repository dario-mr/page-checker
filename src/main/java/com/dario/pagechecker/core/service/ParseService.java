package com.dario.pagechecker.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParseService {

    private final ShutdownService shutdownService;

    @Value("${page.selector}")
    private String selector;

    @Value("${page.attribute}")
    private String attribute;

    public boolean hasAttribute(Document page) {
        Elements elements = page.select(selector);
        if (elements.isEmpty()) {
            log.error("Element not found in page. Selector used: <{}>", selector);
            shutdownService.shutdown(1);
        }
        Element element = elements.get(0);

        return element.hasAttr(attribute);
    }
}
