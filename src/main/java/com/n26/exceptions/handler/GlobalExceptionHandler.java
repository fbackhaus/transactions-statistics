package com.n26.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static com.n26.utils.ValidationMessages.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handle(Exception ex) {
        return (ex.getCause() instanceof InvalidFormatException) ?
                ResponseEntity.unprocessableEntity().build() :
                ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handle(MethodArgumentNotValidException ex) {
        Optional<FieldError> fieldError = Optional.ofNullable(ex.getBindingResult().getFieldError());

        return fieldError.map(error -> handleAccordingValidationError(error.getDefaultMessage()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private ResponseEntity<Void> handleAccordingValidationError(String message) {
        switch (message) {
            case TRANSACTION_TIMESTAMP_IS_IN_THE_FUTURE:
            case TRANSACTION_AMOUNT_IS_NULL:
            case TRANSACTION_TIMESTAMP_IS_NULL:
                return ResponseEntity.unprocessableEntity().build();
            case TRANSACTION_TIMESTAMP_IS_OLD:
                return ResponseEntity.noContent().build();
            default:
                return ResponseEntity.badRequest().build();
        }
    }
}
