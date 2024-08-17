package com.dario.pagechecker.dto;

import java.util.List;

public record Data(
        List<Stock> availableStocks
) {

}
