package com.dario.pagechecker.proxy.ikea.dto;

import java.util.List;

public record Data(
        List<Stock> availableStocks
) {

}
