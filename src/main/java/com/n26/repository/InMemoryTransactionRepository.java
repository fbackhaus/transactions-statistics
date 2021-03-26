package com.n26.repository;

import com.n26.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactionList;

    public InMemoryTransactionRepository() {
        this.transactionList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void save(Transaction entity) {
        transactionList.add(entity);
    }

    @Override
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactionList);
    }

    @Override
    public void deleteAll() {
        transactionList.clear();
    }

    @Override
    public void remove(List<Transaction> transactions) {
        transactionList.removeAll(transactions);
    }
}
