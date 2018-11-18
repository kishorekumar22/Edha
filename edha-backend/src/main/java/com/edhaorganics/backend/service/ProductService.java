package com.edhaorganics.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.beans.Product;
import com.edhaorganics.backend.repo.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;

	public Product addOrEditProduct(Product inputProduct, String principalUser) {
		if (principalUser != null) {
			EdhaUser user = new EdhaUser();
			user.setUsername(principalUser);
			inputProduct.setCreatedBy(user);
		}
		return productRepo.save(inputProduct);
	}

	public List<Product> getProductsList() {
		return productRepo.findByActiveTrue();
	}

	public void softDeleteProduct(String prodId) {
		Product productInDb = productRepo.getOne(Long.parseLong(prodId));
		productInDb.setActive(false);
		productRepo.save(productInDb);
	}

	public List<Product> getProductsListForOrder() {
		return productRepo.findByActiveTrueAndInventoryQtyGreaterThan(0).stream().peek(p -> p.setCreatedBy(null))
				.collect(Collectors.toList());
	}

	public void deleteProduct(String prodId) {
		productRepo.deleteById(Long.parseLong(prodId));

	}

}
