package com.edhaorganics.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.beans.Expense;
import com.edhaorganics.backend.repo.ExpenseRepository;
import com.edhaorganics.backend.repo.UserRepository;

@Service
public class ExpenseService {
	@Autowired
	private ExpenseRepository expenseRepo;
	@Autowired
	private UserRepository userRepo;

	public EdhaUser saveTodaysExpense(String name, List<Expense> expenses) throws Exception {
		EdhaUser user = userRepo.findTop1ByUsername(name);
		if (expenseRepo.findByDate(LocalDate.now()).isEmpty()) {
			expenses.forEach(e -> {
				e.setDate(LocalDate.now());
				e.setUser(user);
			});
			expenseRepo.saveAll(expenses);
			return user;
		} else {
			throw new Exception("Expense entry exists!");
		}

	}

	public List<Expense> getMonthExpense(String username) {
		return expenseRepo.findByUser_usernameOrderByDateAsc(username);
	}

}