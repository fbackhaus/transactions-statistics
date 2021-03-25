package com.n26.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.Assert.*;

public class TransactionValidationTest {
    private static Validator validator;
    private static DateTimeFormatter formatter;

    private final static String TRANSACTION_AMOUNT_IS_NULL = "The transaction amount must be provided";
    private final static String TRANSACTION_TIMESTAMP_IS_NULL = "The transaction timestamp must be provided";
    private final static String TRANSACTION_TIMESTAMP_IS_IN_THE_FUTURE = "The transaction timestamp is in the future";
    private final static String TRANSACTION_TIMESTAMP_IS_OLD = "The transaction timestamp is older than 60 seconds";

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        formatter = DateTimeFormatter.ISO_DATE_TIME;
    }

    @Test
    public void transactionAmountIsNull() {
        Transaction transaction = Transaction.builder()
                .amount(null)
                .timestamp(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertFalse(constraintViolations.isEmpty());

        assertTrue(constraintViolations.stream()
                .anyMatch(transactionConstraintViolation ->
                        transactionConstraintViolation.getMessage().equals(TRANSACTION_AMOUNT_IS_NULL)));
    }

    @Test
    public void timestampIsNull() {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(null)
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertFalse(constraintViolations.isEmpty());

        assertTrue(constraintViolations.stream()
                .anyMatch(transactionConstraintViolation ->
                        transactionConstraintViolation.getMessage().equals(TRANSACTION_TIMESTAMP_IS_NULL)));
    }

    @Test
    public void timestampHasTheCorrectFormatButIsOld() {
        String timestampWithTheCorrectFormat = "2018-07-17T09:59:51.312Z";
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.parse(timestampWithTheCorrectFormat, formatter))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertFalse(constraintViolations.isEmpty());

        assertTrue(constraintViolations.stream()
                .anyMatch(transactionConstraintViolation ->
                        transactionConstraintViolation.getMessage().equals(TRANSACTION_TIMESTAMP_IS_OLD)));
    }

    @Test
    public void transactionIsOlderThan60SecondsShouldFailValidation() {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.now().minusSeconds(61))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertFalse(constraintViolations.isEmpty());

        assertTrue(constraintViolations.stream()
                .anyMatch(transactionConstraintViolation ->
                        transactionConstraintViolation.getMessage().equals(TRANSACTION_TIMESTAMP_IS_OLD)));
    }

    @Test
    public void transactionIsInTheFutureShouldFailValidation() {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.now().plusSeconds(30))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertFalse(constraintViolations.isEmpty());

        assertTrue(constraintViolations.stream()
                .anyMatch(transactionConstraintViolation ->
                        transactionConstraintViolation.getMessage().equals(TRANSACTION_TIMESTAMP_IS_IN_THE_FUTURE)));
    }

    @Test
    public void transaction30SecondsOlderShouldPassValidation() {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.now().minusSeconds(30))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertTrue(constraintViolations.isEmpty());
    }

}