package com.n26.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class Statistics {

    private final BigDecimal sum;
    private final BigDecimal avg;
    private final BigDecimal max;
    private final BigDecimal min;
    private final long count;
}
