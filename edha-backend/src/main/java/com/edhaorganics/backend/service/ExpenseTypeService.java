package com.edhaorganics.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhaorganics.backend.beans.ExpenseType;
import com.edhaorganics.backend.repo.ExpenseTypeRepository;

@Service
public class ExpenseTypeService {
	@Autowired
	private ExpenseTypeRepository typeRepo;

	public ExpenseType saveExpenseType(ExpenseType etype) {
		return typeRepo.save(etype);
	}

	public List<ExpenseType> getAll() {
		return typeRepo.findAll();
	}

	public void deleteExpenseType(ExpenseType eType) {
		typeRepo.deleteById(eType.getId());
	}
}
