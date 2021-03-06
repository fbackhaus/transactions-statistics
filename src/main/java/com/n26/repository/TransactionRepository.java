package com.n26.repository;

import com.n26.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction entity);

    List<Transaction> getTransactions();

    void deleteAll();

    void remove(List<Transaction> transactions);
}
