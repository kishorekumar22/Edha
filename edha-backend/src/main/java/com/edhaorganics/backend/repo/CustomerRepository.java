package com.edhaorganics.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edhaorganics.backend.beans.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	public List<Customer> findByCustomerNameContainingIgnoreCase(String regex);

	public List<Customer> findByIncharge(String salesman);

	public List<Customer> findByIncharge_username(String tempuser);

	public List<Customer> findByCustomerNameContainingIgnoreCaseAndIncharge_FullNameContainingIgnoreCase(String customerName,
			String inchargeName);

	public List<Customer> findByIncharge_FullNameContainingIgnoreCase(String inchargeName);
}
