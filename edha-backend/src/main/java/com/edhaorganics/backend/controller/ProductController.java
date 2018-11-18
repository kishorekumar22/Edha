package com.edhaorganics.backend.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edhaorganics.backend.beans.Product;
import com.edhaorganics.backend.service.ProductService;

@RestController
@RequestMapping("/edha/api/products")
public class ProductController {
	@Autowired
	private ProductService prodService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product addProduct(@RequestBody @Valid Product inputProduct, Principal user) {
		
		return prodService.addOrEditProduct(inputProduct,user.getName());
	}

	@PostMapping("/edit/{prodId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public Product editProduct(@RequestBody @Valid Product inputProduct, @PathVariable String prodId) {
		if (prodId != null && !prodId.isEmpty())
			inputProduct.setId(Long.parseLong(prodId));
		return prodService.addOrEditProduct(inputProduct, null);
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Product> getProducts() {
		return prodService.getProductsList();
	}

	@GetMapping("/remove/{prodId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void removeProduct(@PathVariable String prodId) {
		prodService.softDeleteProduct(prodId);
	}
	
	@GetMapping("/delete/{prodId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteProduct(@PathVariable String prodId) {
		prodService.deleteProduct(prodId);
	}
	@GetMapping("/list-for-order")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Product> getProductsListForOrder() {
		return prodService.getProductsListForOrder();
	}

}
