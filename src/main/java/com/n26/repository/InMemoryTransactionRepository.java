package com.n26.repository;

import com.n26.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final ConcurrentMap<LocalDateTime, BigDecimal> transactionConcurrentMap;

    public InMemoryTransactionRepository() {
        this.transactionConcurrentMap = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Transaction entity) {
        transactionConcurrentMap.put(entity.getTimestamp(), entity.getAmount());
    }

    @Override
    public List<BigDecimal> getTransactionAmounts() {
        return new ArrayList<>(transactionConcurrentMap.values());
    }

    @Override
    public void deleteAll() {
        transactionConcurrentMap.clear();
    }
}
