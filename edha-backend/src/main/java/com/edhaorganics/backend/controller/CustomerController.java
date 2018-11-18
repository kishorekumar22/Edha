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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edhaorganics.backend.beans.Customer;
import com.edhaorganics.backend.service.CustomerService;

@RestController
@RequestMapping("/edha/api/customer")
public class CustomerController {
	@Autowired
	private CustomerService custService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public Customer addCustomer(@RequestBody @Valid Customer inputCustomer,Principal user) {
		return custService.addOrEditCustomer(inputCustomer,user.getName());
	}

	@PostMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public Customer editCustomer(@RequestBody @Valid Customer inputCustomer) {
		return custService.addOrEditCustomer(inputCustomer,null);
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Customer> getCustomers() {
		return custService.getCustomersList();
	}

	@GetMapping("/get/{customerNameREgex}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Customer> getCustomerByRegex(@PathVariable String customerNameREgex) {
		return custService.getCustomers(customerNameREgex);

	}

	@GetMapping("/list-assigned-customer/{salesman}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Customer> getAssignedCustomer(@PathVariable String salesman) {
		return custService.geAssignedCustomers(salesman);
	}

	@GetMapping("/list-customers-by-filter/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Customer> getcutomersByFilter(@RequestParam(required = false) String customerName,
			@RequestParam(required = false) String inchargeName) {
		return custService.getCustomersListByFilter(customerName, inchargeName);
	}

}
