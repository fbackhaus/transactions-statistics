package com.n26.service;

import com.n26.model.Transaction;
import com.n26.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionRepository repository;

    public void createTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    public List<BigDecimal> getValidTransactionAmounts() {
        List<Transaction> transactions = repository.getTransactions();
        return transactions.stream()
                .filter(Transaction::shouldBeConsideredForStatistics)
                .map(Transaction::getAmount)
                .collect(Collectors.toList());
    }
}
