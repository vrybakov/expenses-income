package ru.ryb.budget.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IExpensesForUnload extends AmountExpenses{

    LocalDate getCreateDate();

    BigDecimal getAmount();

}
