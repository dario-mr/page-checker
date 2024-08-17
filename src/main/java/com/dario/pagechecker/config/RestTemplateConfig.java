package com.dario.pagechecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateConfig {

        @Bean
        RestTemplate restTemplate() {
            var requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(10_000);
            requestFactory.setReadTimeout(5_000);

            return new RestTemplate(requestFactory);
        }
}

