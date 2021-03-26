package com.n26.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Statistics {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal sum;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal avg;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal max;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal min;

    private final long count;
}
