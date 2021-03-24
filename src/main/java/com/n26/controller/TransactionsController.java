package com.n26.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionsController {

    @PostMapping()
    public ResponseEntity<Void> createNewTransaction() {
        //TODO: Create POST Endpoint
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactions() {
        //TODO: Create DELETE Endpoint
        return ResponseEntity.noContent().build();
    }
}
