package com.n26.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.n26.utils.ValidationMessages.TRANSACTION_AMOUNT_IS_NULL;
import static com.n26.utils.ValidationMessages.TRANSACTION_TIMESTAMP_IS_NULL;

@Getter
@Builder
@ValidTransaction
public class Transaction {

    @Valid
    @NotNull(message = TRANSACTION_AMOUNT_IS_NULL)
    private final BigDecimal amount;

    @Valid
    @NotNull(message = TRANSACTION_TIMESTAMP_IS_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime timestamp;

    public boolean shouldBeConsideredForStatistics() {
        return timestamp.isAfter(LocalDateTime.now().minusSeconds(60));
    }
}
