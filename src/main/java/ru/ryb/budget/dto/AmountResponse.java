package ru.ryb.budget.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class AmountResponse {

    private BigDecimal amount;

}
