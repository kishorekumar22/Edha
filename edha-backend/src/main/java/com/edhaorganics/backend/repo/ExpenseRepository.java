package com.edhaorganics.backend.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edhaorganics.backend.beans.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByDate(LocalDate now);

	List<Expense> findByUser_usernameOrderByDateAsc(String username);

}
