package com.n26.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static com.n26.utils.ValidationMessages.TRANSACTION_TIMESTAMP_IS_IN_THE_FUTURE;
import static com.n26.utils.ValidationMessages.TRANSACTION_TIMESTAMP_IS_OLD;

public class TransactionValidator implements ConstraintValidator<ValidTransaction, Transaction> {

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext context) {
        if (transaction.getTimestamp() == null || transaction.getAmount() == null) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();

        if (isTheTransactionTimestampInTheFuture(transaction.getTimestamp(), now)) {
            buildConstraintViolation(context, TRANSACTION_TIMESTAMP_IS_IN_THE_FUTURE);
            return false;
        }

        if (isTheTransactionOlderThan60Seconds(transaction.getTimestamp(), now)) {
            buildConstraintViolation(context, TRANSACTION_TIMESTAMP_IS_OLD);
            return false;
        }

        return true;
    }

    private boolean isTheTransactionTimestampInTheFuture(LocalDateTime localDateTime, LocalDateTime now) {
        return !localDateTime.isBefore(now);
    }

    private boolean isTheTransactionOlderThan60Seconds(LocalDateTime localDateTime, LocalDateTime now) {
        return !localDateTime.isAfter(now.minusSeconds(60));
    }

    private void buildConstraintViolation(ConstraintValidatorContext context, String message) {
        context
                .buildConstraintViolationWithTemplate(message)
                .addPropertyNode("timestamp")
                .addConstraintViolation();
    }
}
