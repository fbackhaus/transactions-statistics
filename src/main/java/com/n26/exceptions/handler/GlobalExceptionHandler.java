package com.n26.exceptions.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.n26.utils.ValidationMessages.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
    })
    public ResponseEntity<Void> handle() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
    })
    public ResponseEntity<Void> handle(MethodArgumentNotValidException ex) {

        switch (ex.getBindingResult().getFieldError().getDefaultMessage()) {
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
