package com.dario.pagechecker.core.service.ikea;

import com.dario.pagechecker.proxy.ikea.dto.IkeaResponse;

public interface IkeaGateway {

    IkeaResponse get(String url, String ikeaApiKey);
}
