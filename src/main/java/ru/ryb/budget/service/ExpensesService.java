package ru.ryb.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.model.Expenses;
import ru.ryb.budget.repository.ExpensesRepository;

import java.time.LocalDate;
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

    public List<Expenses> getListExpenses(PeriodExpenses periodExpenses) {
        return expensesRepository.findAllByCategoryIdAndCreateDateBetween(periodExpenses.getCategoryId(), periodExpenses.getBeginDate(), periodExpenses.getEndDate());
    }

    public Expenses subtract(Expenses request) {
        Expenses expenses = expensesRepository.findByCategoryIdAndCreateDate(request.getCategoryId(), request.getCreateDate());
        expenses.setAmount(expenses.getAmount().subtract(request.getAmount()));
        return expensesRepository.save(expenses);
    }

}
