package com.dario.pagechecker.config;

import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        // http client with cookies disabled, to avoid useless warnings
        var httpClient = HttpClients.custom()
                .disableCookieManagement()
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(10_000);
        requestFactory.setReadTimeout(5_000);

        return new RestTemplate(requestFactory);
    }
}

