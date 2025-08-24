package ru.ryb.budget.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PeriodExpenses {

    private LocalDate beginDate;
    private LocalDate endDate;
    private Integer categoryId;

}
