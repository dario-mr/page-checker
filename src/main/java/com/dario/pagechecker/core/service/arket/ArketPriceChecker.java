package com.dario.pagechecker.core.service.arket;

import com.dario.pagechecker.core.service.DownloadService;
import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArketPriceChecker {

    private final DownloadService downloadService;
    private final EmailService emailService;
    private final ShutdownService shutdownService;

    @Value("${arket-checker.url}")
    private String url;

    @Value("${arket-checker.desired-price}")
    private BigDecimal desiredPrice;

    public void checkPrice() {
        log.info("Checking Arket item {} with desired price {}...", url, desiredPrice);

        var page = downloadService.download(url);
        var productDetails = page.getElementsByClass("producttile-details").get(0);
        var currentPrice = new BigDecimal(productDetails.getElementsByClass("price").get(0).text());
        var productName = productDetails.getElementsByClass("productName").get(0).text();

        if (currentPrice.compareTo(desiredPrice) <= 0) {
            log.info("Current price {} is lower than desired price {}!", currentPrice, desiredPrice);
            emailService.send(
                    format("Arket: \"%s\" price dropped!", productName),
                    format("\"%s\" price [%s] fell below desired price [%s].\n\nLink: %s", productName, currentPrice, desiredPrice, url));
            shutdownService.shutdown(0);
        }
    }
}
