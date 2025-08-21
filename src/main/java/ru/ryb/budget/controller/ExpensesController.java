package ru.ryb.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ryb.budget.dto.ExpensesListResponseDTO;
import ru.ryb.budget.dto.PeriodExpenses;
import ru.ryb.budget.model.Expenses;
import ru.ryb.budget.service.ExpensesService;

@RestController
@RequestMapping(value = "v1/category/{categoryId}/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;

    @Autowired
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @PostMapping
    public ResponseEntity<Expenses> create(@RequestBody Expenses request,
                                           @PathVariable(name = "categoryId") int categoryId) {
        request.setCategoryId(categoryId);
        return ResponseEntity.ok(expensesService.create(request));
    }

    @PutMapping
    public ResponseEntity<Expenses> add(@RequestBody Expenses request) {
        return ResponseEntity.ok(expensesService.add(request));
    }

    @GetMapping("/list")
    public ResponseEntity<ExpensesListResponseDTO> getListExpenses(@RequestBody PeriodExpenses request, @PathVariable int categoryId) {
        request.setCategoryId(categoryId);
        ExpensesListResponseDTO response = new ExpensesListResponseDTO();
        response.setExpenses(expensesService.getListExpenses(request));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/subtract")
    public ResponseEntity<Expenses> subtract(@RequestBody Expenses request) {
        return ResponseEntity.ok(expensesService.subtract(request));
    }


}
