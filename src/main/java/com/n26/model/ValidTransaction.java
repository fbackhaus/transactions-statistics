package com.n26.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TransactionValidator.class)
public @interface ValidTransaction {

    String message() default "Transaction data not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
