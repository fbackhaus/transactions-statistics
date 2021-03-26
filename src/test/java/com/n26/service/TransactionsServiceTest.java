package com.n26.service;

import com.n26.model.Transaction;
import com.n26.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsServiceTest {

    @Mock
    private TransactionRepository repository;

    private TransactionsService transactionsService;

    private List<BigDecimal> transactionAmounts;

    @Before
    public void setup() {
        transactionsService = new TransactionsService(repository);
    }

    @Test
    public void getValidTransactionsReturnsEmptyListIfTransactionsAreOld() {
        givenAListOfOldTransactions();
        whenIGetTheListOfValidTransactions();
        thenTheTransactionListIsEmpty();
    }

    @Test
    public void getValidTransactionsReturnsOneValidTransactionAndOneOld() {
        givenAListOfOneValidAndOneOldTransaction();
        whenIGetTheListOfValidTransactions();
        thenTheTransactionListShouldHaveOnlyOneElement();
    }

    @Test
    public void getValidTransactionsReturnsAllTransactionsWhenTheyHappenedInThaLast60Seconds() {
        givenAListOfValidTransactions();
        whenIGetTheListOfValidTransactions();
        thenTheTransactionListShouldHaveAllElements();
    }

    @Test
    public void deletesTransactionsWhenThe60SecondsExpire() throws InterruptedException {
        givenAListOfValidTransactions();

        long deletedTransactions = transactionsService.deleteOldTransactions();
        theFirstTimeNoTransactionsNeededDeletion(deletedTransactions);

        waitFor5Seconds();

        deletedTransactions = transactionsService.deleteOldTransactions();
        theSecondTimeAllTransactionsWereDeleted(deletedTransactions);
    }

    private void theSecondTimeAllTransactionsWereDeleted(long deletedTransactions) {
        Assert.assertEquals(3, deletedTransactions);
    }

    private void waitFor5Seconds() throws InterruptedException {
        Thread.sleep(5000);
    }

    private void theFirstTimeNoTransactionsNeededDeletion(long deletedTransactions) {
        Assert.assertEquals(0, deletedTransactions);
    }

    private void thenTheTransactionListIsEmpty() {
        Assert.assertTrue(transactionAmounts.isEmpty());
    }

    private void thenTheTransactionListShouldHaveOnlyOneElement() {
        Assert.assertEquals(1, transactionAmounts.size());
        Assert.assertEquals(BigDecimal.ONE, transactionAmounts.get(0));
    }

    private void thenTheTransactionListShouldHaveAllElements() {
        Assert.assertEquals(3, transactionAmounts.size());
        transactionAmounts
                .forEach(amount -> Assert.assertEquals(amount, BigDecimal.TEN));
    }

    private void whenIGetTheListOfValidTransactions() {
        transactionAmounts = transactionsService.getValidTransactionAmounts();
    }

    private void givenAListOfOldTransactions() {
        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(getTransaction(
                        BigDecimal.TEN,
                        LocalDateTime.now().minusHours(10)));

        transactionList.add(getTransaction(
                BigDecimal.ONE,
                LocalDateTime.now().minusHours(2)));

        when(repository.getTransactions()).thenReturn(transactionList);
    }

    private void givenAListOfOneValidAndOneOldTransaction() {
        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(getTransaction(
                BigDecimal.TEN,
                LocalDateTime.now().minusHours(10)));

        transactionList.add(getTransaction(
                BigDecimal.ONE,
                LocalDateTime.now().minusSeconds(10)));

        when(repository.getTransactions()).thenReturn(transactionList);
    }

    private void givenAListOfValidTransactions() {
        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(getTransaction(
                BigDecimal.TEN,
                LocalDateTime.now().minusSeconds(58)));

        transactionList.add(getTransaction(
                BigDecimal.TEN,
                LocalDateTime.now().minusSeconds(57)));

        transactionList.add(getTransaction(
                BigDecimal.TEN,
                LocalDateTime.now().minusSeconds(56)));

        when(repository.getTransactions()).thenReturn(transactionList);
    }

    private Transaction getTransaction(BigDecimal amount, LocalDateTime timestamp) {
        return Transaction.builder()
                .amount(amount)
                .timestamp(timestamp)
                .build();
    }
}