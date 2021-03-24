package com.n26.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransactionValidator implements ConstraintValidator<ValidTransaction, Transaction> {

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
