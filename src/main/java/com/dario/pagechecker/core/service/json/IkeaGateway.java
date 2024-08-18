package com.dario.pagechecker.core.service.json;

import com.dario.pagechecker.proxy.ikea.dto.IkeaResponse;

public interface IkeaGateway {

    IkeaResponse get(String url, String ikeaApiKey);
}
