package com.n26.service;

import com.n26.model.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    private TransactionsService transactionsService;

    private StatisticsService statisticsService;

    private Statistics statistics;

    @Before
    public void setup() {
        statisticsService = new StatisticsService(transactionsService);
    }

    @Test
    public void ifTransactionListIsEmptyAllStatisticsAreZero() {
        givenAnEmptyListOfAmounts();
        whenIGenerateStatistics();
        thenAllStatisticsAreZeroWith2DecimalPlaces();
    }

    @Test
    public void allStatisticsAreGeneratedCorrectly() {
        givenAListOfAmounts();
        whenIGenerateStatistics();
        thenAllStatisticsAreCorrectAndHave2DecimalPlaces();
    }

    private void thenAllStatisticsAreCorrectAndHave2DecimalPlaces() {
        assertEquals(new BigDecimal(7)
                .setScale(2, RoundingMode.HALF_UP), statistics.getAvg());
        assertEquals(BigDecimal.TEN
                .setScale(2, RoundingMode.HALF_UP), statistics.getMax());
        assertEquals(BigDecimal.ONE
                .setScale(2, RoundingMode.HALF_UP), statistics.getMin());
        assertEquals(new BigDecimal(21)
                .setScale(2, RoundingMode.HALF_UP), statistics.getSum());
        assertEquals(3, statistics.getCount());
    }

    private void thenAllStatisticsAreZeroWith2DecimalPlaces() {
        BigDecimal zeroWith2Decimals = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(zeroWith2Decimals, statistics.getAvg());
        assertEquals(zeroWith2Decimals, statistics.getMax());
        assertEquals(zeroWith2Decimals, statistics.getMin());
        assertEquals(zeroWith2Decimals, statistics.getSum());
        assertEquals(0, statistics.getCount());
    }

    private void whenIGenerateStatistics() {
        statistics = statisticsService.generateStatistics();
    }

    private void givenAnEmptyListOfAmounts() {
        when(transactionsService.getValidTransactionAmounts())
                .thenReturn(new ArrayList<>());
    }

    private void givenAListOfAmounts() {
        List<BigDecimal> amountList = new ArrayList<>();
        amountList.add(BigDecimal.TEN);
        amountList.add(BigDecimal.ONE);
        amountList.add(BigDecimal.TEN);

        when(transactionsService.getValidTransactionAmounts())
                .thenReturn(amountList);
    }
}