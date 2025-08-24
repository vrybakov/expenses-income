package ru.ryb.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ryb.budget.model.Expenses;
import ru.ryb.budget.model.IExpensesForUnload;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    List<Expenses> findAllByCategoryIdAndCreateDateBetween(int categoryId, LocalDate beginDate, LocalDate endDate);

    Expenses findByCategoryIdAndCreateDate(int categoryId, LocalDate createDate);

    List<IExpensesForUnload> findAllUnloadByCategoryIdAndCreateDateBetween(int categoryId, LocalDate beginDate, LocalDate endDate);
}
