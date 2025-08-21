package ru.ryb.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryb.budget.dto.AmountResponse;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.service.AnalyzerService;

@RestController
@RequestMapping("v1/analyzer")
public class AnalyzerController {

    private final AnalyzerService analyzerService;

    @Autowired
    public AnalyzerController(AnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @GetMapping("/sum")
    public ResponseEntity<AmountResponse> getAmountForPeriod(@RequestBody PeriodExpenses request) {
        return ResponseEntity.ok(analyzerService.getAmountForPeriod(request));
    }

}
