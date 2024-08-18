package com.dario.pagechecker.proxy.ikea.dto;

import java.util.List;

public record Stock(
        List<Probability> probabilities
) {

}
