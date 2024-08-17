package com.dario.pagechecker.dto;

import java.util.List;

public record Stock(
        List<Probability> probabilities
) {

}
