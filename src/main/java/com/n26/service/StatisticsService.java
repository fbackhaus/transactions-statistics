package com.n26.service;

import com.n26.model.Statistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionsService transactionsService;

    public Statistics generateStatistics() {
        List<BigDecimal> transactionAmounts = transactionsService.getValidTransactionAmounts();

        long count = transactionAmounts.size();
        BigDecimal sum = calculateSum(transactionAmounts);

        return Statistics.builder()
                .sum(sum
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .avg(calculateAvg(sum, count)
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .max(calculateMax(transactionAmounts)
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .min(calculateMin(transactionAmounts)
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .count(count)
                .build();
    }

    private BigDecimal calculateSum(List<BigDecimal> transactionAmounts) {
        return transactionAmounts.parallelStream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAvg(BigDecimal sum, long count) {

        if (count == 0) {
            return BigDecimal.ZERO;
        }

        return sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMax(List<BigDecimal> transactionAmounts) {
        return transactionAmounts.parallelStream()
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateMin(List<BigDecimal> transactionAmounts) {
        return transactionAmounts.parallelStream()
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }
}
