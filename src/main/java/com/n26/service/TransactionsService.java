package com.n26.service;

import com.n26.model.Transaction;
import com.n26.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionRepository repository;

    public void createTransaction(Transaction transaction) {
        repository.save(transaction);
    }
}
