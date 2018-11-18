package com.edhaorganics.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edhaorganics.backend.beans.Customer;
import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.repo.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	public Customer addOrEditCustomer(Customer inputCustomer, String principalUser) {
		if(principalUser != null){
			EdhaUser user = new EdhaUser();
			user.setUsername(principalUser);
			inputCustomer.setCreatedBy(user);
		}
		return customerRepo.save(inputCustomer);
	}

	public List<Customer> getCustomersList() {
		return customerRepo.findAll();
	}

	public List<Customer> getCustomers(String regex) {
		return customerRepo.findByCustomerNameContainingIgnoreCase(regex);
	}

	public List<Customer> geAssignedCustomers(String salesman) {
		return customerRepo.findByIncharge_username(salesman);
	}

	public List<Customer> getCustomersListByFilter(String customerName, String inchargeName) {
		if (!StringUtils.isEmpty(customerName) && !StringUtils.isEmpty(inchargeName)) {
			return customerRepo.findByCustomerNameContainingIgnoreCaseAndIncharge_FullNameContainingIgnoreCase(customerName,
					inchargeName);
		} else if (!StringUtils.isEmpty(customerName)) {
			return customerRepo.findByCustomerNameContainingIgnoreCase(customerName);
		} else {
			return customerRepo.findByIncharge_FullNameContainingIgnoreCase(inchargeName);
		}
	}

}
