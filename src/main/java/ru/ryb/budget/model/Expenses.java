package ru.ryb.budget.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name="expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

//    @ManyToOne
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Category owner;

}
