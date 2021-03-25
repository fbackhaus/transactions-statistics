package com.n26.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionsService transactionsService;

    public void generateStatistics() {
        List<BigDecimal> transactionAmounts = transactionsService.getValidTransactionAmounts();

    }
}
