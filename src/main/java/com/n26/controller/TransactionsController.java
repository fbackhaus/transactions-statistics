package com.n26.controller;

import com.n26.model.Transaction;
import com.n26.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createNewTransaction(@Valid @RequestBody Transaction transaction) {
        transactionsService.createTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAllTransactions() {
        transactionsService.deleteTransactions();
        return ResponseEntity.noContent().build();
    }
}
