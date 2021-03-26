package com.n26.controller;

import com.n26.model.Transaction;
import com.n26.service.TransactionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionsController {

    private final TransactionsService transactionsService;

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Request body invalid"),
            @ApiResponse(responseCode = "204", description = "Transaction not created, given that it's older than 60 seconds"),
            @ApiResponse(responseCode = "422", description = "Transaction not created, given that any of the fields is not parsable, or the transaction date is in the future")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createNewTransaction(@Valid @RequestBody Transaction transaction) {
        log.info("Received request to add transaction: {}", transaction);
        transactionsService.createTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete all transactions")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAllTransactions() {
        log.info("Received request to delete all transactions");
        transactionsService.deleteTransactions();
        return ResponseEntity.noContent().build();
    }
}
