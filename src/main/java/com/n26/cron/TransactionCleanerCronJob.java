package com.n26.cron;

import com.n26.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class TransactionCleanerCronJob {

    private final TransactionsService transactionsService;

    @Scheduled(fixedDelay = 60000)
    public void removeOldTransactions() {
        log.info("Running transaction cleaner cron job");
        long deletedTransactions = transactionsService.deleteOldTransactions();
        log.info("Execution finished. Deleted {} transactions", deletedTransactions);
    }
}
