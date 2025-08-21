package ru.ryb.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryb.budget.dto.AmountResponse;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.model.AmountExpenses;
import ru.ryb.budget.repository.AnalyzerRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzerService {

    private final AnalyzerRepository analyzerRepository;

    @Autowired
    public AnalyzerService(AnalyzerRepository analyzerRepository) {
        this.analyzerRepository = analyzerRepository;
    }

    public AmountResponse getAmountForPeriod(PeriodExpenses periodExpenses) {
        LocalDate beginDate = LocalDate.of(periodExpenses.getYear(), periodExpenses.getMonth(), 1);
        LocalDate endDate = beginDate.with(TemporalAdjusters.lastDayOfMonth());
        List<AmountExpenses> expensesList = periodExpenses.getCategoryId() != null
                ? analyzerRepository.findByCategoryIdAndCreateDateBetween(periodExpenses.getCategoryId(), beginDate, endDate)
                : analyzerRepository.findByCreateDateBetween(beginDate, endDate);
        List<BigDecimal> amountList = new ArrayList<>();
        expensesList.forEach(expenses -> amountList.add(expenses.getAmount()));
        BigDecimal sum = amountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        AmountResponse response = new AmountResponse();
        response.setAmount(sum);
        return response;
    }

}
