package com.n26.repository;

import com.n26.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository {

    void save(Transaction entity);

    List<BigDecimal> getTransactionAmounts();

    void deleteAll();
}
