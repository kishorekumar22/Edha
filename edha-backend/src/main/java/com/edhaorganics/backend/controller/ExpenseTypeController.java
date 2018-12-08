package com.edhaorganics.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edhaorganics.backend.beans.ExpenseType;
import com.edhaorganics.backend.service.ExpenseTypeService;

@RestController
@RequestMapping("/edha/api/expense-type")
public class ExpenseTypeController {
	@Autowired
	private ExpenseTypeService expenseTypeService;

	@PostMapping("/save")
	public ExpenseType saveExpenseType(@RequestBody ExpenseType eType) {
		return expenseTypeService.saveExpenseType(eType);
	}

	@GetMapping("/get")
	public List<ExpenseType> fetchExpenseTypes() {
		return expenseTypeService.getAll();
	}

	@PostMapping("/delete")
	public void deleteExpenseType(@RequestBody ExpenseType eType) {
		expenseTypeService.deleteExpenseType(eType);
	}
}
