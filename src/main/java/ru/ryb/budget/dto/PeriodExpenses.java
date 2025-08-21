package ru.ryb.budget.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PeriodExpenses {

    private Integer year;
    private Integer month;
    private Integer categoryId;

}
