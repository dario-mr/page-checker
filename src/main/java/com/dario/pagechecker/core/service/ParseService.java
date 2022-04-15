package com.dario.pagechecker.core.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class ParseService {

    @Value("${page.selector}")
    private String selector;

    @Value("${page.attribute}")
    private String attribute;

    public boolean hasAttribute(Document page) {
        Elements elements = page.select(selector);
        if (elements.isEmpty()) {
            throw new RuntimeException(format("Element not found in page. Selector used: \"%s\"", selector));
        }
        Element element = elements.get(0);

        return element.hasAttr(attribute);
    }
}
