package ru.ryb.budget.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.ryb.budget.model.Expenses;

import java.util.List;

@Getter
@Setter
@ToString
public class ExpensesListResponseDTO {

    private List<Expenses> expenses;

}
