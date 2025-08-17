package ru.ryb.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryb.budget.model.Expenses;
import ru.ryb.budget.repository.ExpensesRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ExpensesService {

    private final ExpensesRepository expensesRepository;

    @Autowired
    public ExpensesService(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    public Expenses create(Expenses request) {
        LocalDate currentDate = LocalDate.now();
        request.setCreateDate(currentDate);
        Expenses expenses = expensesRepository.findByCategoryIdAndCreateDate(request.getCategoryId(), currentDate);

        if (expenses != null) {
            //TODO Нужно реализовать обработку ошибки
        }

        return expensesRepository.save(request);
    }

    public Expenses add(Expenses request) {
        Expenses expenses = expensesRepository.findByCategoryIdAndCreateDate(request.getCategoryId(), request.getCreateDate());

        if (expenses != null) {
            request.setAmount(request.getAmount().add(expenses.getAmount()));
        }
        return expensesRepository.save(request);
    }

    public List<Expenses> getListExpenses(int categoryId, int year, int month) {
        LocalDate beginDate = LocalDate.of(year, month, 1);
        LocalDate endDate = beginDate.with(TemporalAdjusters.lastDayOfMonth());

        return expensesRepository.findAllByCategoryIdAndCreateDateBetween(categoryId, beginDate, endDate);
    }

    public Expenses subtract(Expenses request) {
        Expenses expenses = expensesRepository.findByCategoryIdAndCreateDate(request.getCategoryId(), request.getCreateDate());
        expenses.setAmount(expenses.getAmount().subtract(request.getAmount()));
        return expensesRepository.save(expenses);
    }

}
