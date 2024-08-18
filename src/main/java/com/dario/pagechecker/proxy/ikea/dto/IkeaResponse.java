package com.dario.pagechecker.proxy.ikea.dto;

import java.util.List;

public record IkeaResponse(
        List<Data> data
) {

}
