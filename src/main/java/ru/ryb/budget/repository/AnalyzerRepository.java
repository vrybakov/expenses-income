package ru.ryb.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ryb.budget.model.AmountExpenses;
import ru.ryb.budget.model.Expenses;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnalyzerRepository extends JpaRepository<Expenses, Integer> {

    List<AmountExpenses> findByCategoryIdAndCreateDateBetween(int categoryId, LocalDate beginDate, LocalDate endDate);

    List<AmountExpenses> findByCreateDateBetween(LocalDate beginDate, LocalDate endDate);

}
