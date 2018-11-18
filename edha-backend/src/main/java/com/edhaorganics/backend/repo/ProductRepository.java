package com.edhaorganics.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edhaorganics.backend.beans.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{

	List<Product> findByActiveTrue();
	
	List<Product> findByActiveTrueAndInventoryQtyGreaterThan(float qty);

	

}
