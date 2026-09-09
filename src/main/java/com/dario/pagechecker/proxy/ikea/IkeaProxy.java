package com.dario.pagechecker.proxy.ikea;

import com.dario.pagechecker.core.service.ikea.IkeaGateway;
import com.dario.pagechecker.proxy.ikea.dto.IkeaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
@RegisterReflectionForBinding(IkeaResponse.class)
public class IkeaProxy implements IkeaGateway {

    private final RestTemplate restTemplate;

    @Override
    public IkeaResponse get(String url, String ikeaApiKey) {
        var headers = new HttpHeaders();
        headers.set("x-client-id", ikeaApiKey);
        var requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, GET, requestEntity, IkeaResponse.class).getBody();
    }
}
