package com.edhaorganics.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.beans.Expense;
import com.edhaorganics.backend.beans.ExpenseProjection;
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

	public Map<LocalDate, List<ExpenseProjection>> getMonthExpense(String username) {
		List<ExpenseProjection> expense = expenseRepo.findByUser_usernameOrderByDateAsc(username);
		Map<LocalDate, List<ExpenseProjection>> monthlyExpense = new HashMap<>();
		expense.stream().forEach(e -> {
			if (monthlyExpense.containsKey(e.getDate()))
				monthlyExpense.get(e.getDate()).add(e);
			else
				monthlyExpense.put(e.getDate(), new ArrayList<ExpenseProjection>(Arrays.asList(e)));
		});
		return monthlyExpense;

	}
}