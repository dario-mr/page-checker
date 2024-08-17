package com.dario.pagechecker.core.service.json;

import com.dario.pagechecker.dto.IkeaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
public class RestService {

    private final RestTemplate restTemplate;

    public IkeaResponse get(String url) {
        var headers = new HttpHeaders();
        headers.set("x-client-id", "b6c117e5-ae61-4ef5-b4cc-e0b1e37f0631");
        var requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, GET, requestEntity, IkeaResponse.class).getBody();
    }
}
