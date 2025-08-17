package ru.ryb.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ryb.budget.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
