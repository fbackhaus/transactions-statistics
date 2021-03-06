package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "statistics", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Generate statistics based in the last 60 seconds transactions")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Statistics> getTransactionStatistics() {
        log.info("Generating transactions statistics");
        return ResponseEntity
                .ok(statisticsService.generateStatistics());
    }

}
