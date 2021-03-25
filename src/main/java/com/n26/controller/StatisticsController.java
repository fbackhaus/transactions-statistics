package com.n26.controller;

import com.n26.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping()
    public ResponseEntity<Void> getTransactionStatistics() {
        statisticsService.generateStatistics();
        return ResponseEntity.ok().build();
    }

}
