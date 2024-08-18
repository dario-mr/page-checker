package com.dario.pagechecker.core.service.html;

import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParseService {

    private final ShutdownService shutdownService;
    private final EmailService emailService;

    @Value("${html-checker.selector}")
    private String selector;

    @Value("${html-checker.attribute}")
    private String attribute;

    public boolean hasAttribute(Document page) {
        var elements = page.select(selector);
        if (elements.isEmpty()) {
            var errorMessage = format("Element not found in page, using selector: [%s]", selector);

            log.error(errorMessage);
            emailService.send("Element not found", errorMessage);
            shutdownService.shutdown(1);
        }

        var element = elements.get(0);
        return element.hasAttr(attribute);
    }
}
