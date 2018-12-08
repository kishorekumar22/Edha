package com.edhaorganics.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhaorganics.backend.beans.DiscountType;
import com.edhaorganics.backend.repo.DiscountTypeRepository;

@Service
public class DiscountTypeService {
	@Autowired
	private DiscountTypeRepository typeRepo;

	public DiscountType saveDiscountType(DiscountType etype) {
		return typeRepo.save(etype);
	}

	public List<DiscountType> getAll() {
		return typeRepo.findAll();
	}

	public void deleteDiscountType(DiscountType eType) {
		typeRepo.deleteById(eType.getId());
	}
}
