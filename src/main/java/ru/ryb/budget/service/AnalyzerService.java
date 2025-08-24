package ru.ryb.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryb.budget.dto.AmountResponse;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.model.AmountExpenses;
import ru.ryb.budget.repository.AnalyzerRepository;

import java.math.BigDecimal;
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
        List<AmountExpenses> expensesList;
        if (periodExpenses.getEndDate() == null && periodExpenses.getBeginDate() == null) {
            expensesList = analyzerRepository.findAllByCategoryId(periodExpenses.getCategoryId());
        } else {
            expensesList = periodExpenses.getCategoryId() != null
                    ? analyzerRepository.findByCategoryIdAndCreateDateBetween(periodExpenses.getCategoryId(), periodExpenses.getBeginDate(), periodExpenses.getEndDate())
                    : analyzerRepository.findByCreateDateBetween(periodExpenses.getBeginDate(), periodExpenses.getEndDate());
        }
        AmountResponse response = new AmountResponse();
        response.setAmount(calculateSum(expensesList));
        return response;
    }

    private BigDecimal calculateSum(List<AmountExpenses> expensesList) {
        List<BigDecimal> amountList = new ArrayList<>();
        expensesList.forEach(expenses -> amountList.add(expenses.getAmount()));
        return amountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
