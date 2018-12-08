package com.edhaorganics.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edhaorganics.backend.beans.DiscountType;
import com.edhaorganics.backend.service.DiscountTypeService;

@RestController
@RequestMapping("/edha/api/discount-type")
public class DiscountTypeController {
	@Autowired
	private DiscountTypeService DiscountTypeService;

	@PostMapping("/save")
	public DiscountType saveDiscountType(@RequestBody DiscountType eType) {
		return DiscountTypeService.saveDiscountType(eType);
	}

	@GetMapping("/get")
	public List<DiscountType> fetchDiscountTypes() {
		return DiscountTypeService.getAll();
	}

	@PostMapping("/delete")
	public void deleteDiscountType(@RequestBody DiscountType eType) {
		DiscountTypeService.deleteDiscountType(eType);
	}
}
