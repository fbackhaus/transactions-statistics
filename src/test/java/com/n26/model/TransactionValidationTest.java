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

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        formatter = DateTimeFormatter.ISO_DATE_TIME;
    }

    @Test
    public void validTransaction() {

        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void transactionAmountIsNull() {
        Transaction transaction = Transaction.builder()
                .amount(null)
                .timestamp(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "must not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void timestampIsNull() {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(null)
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "must not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void timestampHasADifferentFormat() {
        String timeStampWithADifferentFormat = "2018-02-27 15:35:20.311";
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.parse(timeStampWithADifferentFormat, formatter))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "must not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void timestampHasTheCorrectFormat() {
        String timestampWithTheCorrectFormat = "2018-07-17T09:59:51.312Z";
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.parse(timestampWithTheCorrectFormat, formatter))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void transactionIsOlderThan60Seconds() {
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(12.3343))
                .timestamp(LocalDateTime.now().minusSeconds(61))
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations =
                validator.validate(transaction);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Transaction data not valid",
                constraintViolations.iterator().next().getMessage()
        );
    }

}