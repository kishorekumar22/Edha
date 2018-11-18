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

import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.beans.Expense;
import com.edhaorganics.backend.service.ExpenseService;
import com.edhaorganics.backend.service.UserService;

@RestController
@RequestMapping("/edha/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public EdhaUser createNewUser(@RequestBody @Valid EdhaUser user, Principal principalUser) {
		return userService.register(user, principalUser.getName());
	}

	@PostMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public EdhaUser editUser(@RequestBody @Valid EdhaUser user) {
		return userService.saveEditedUser(user);
	}

	@GetMapping("/login")
	public Principal doLogin(Principal user) {
		return user;
	}

	@GetMapping("/check-username/{userName}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public boolean checkUsername(@PathVariable String userName) {
		return userService.checkUserName(userName);
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<EdhaUser> listUsers() {
		return userService.listUsers();
	}

	@GetMapping("/get/{userName}")
	public EdhaUser getUser(@PathVariable String userName) {
		return userService.getUser(userName);
	}

	@GetMapping("/list-active-users")
	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_SALESMAN')")
	public List<EdhaUser> listActiveUsers() {
		return userService.getActiveUsers();
	}

	@PostMapping("/change-password")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public EdhaUser changePassword(@RequestBody String newPwd, Principal user) {
		return userService.updatePassword(user.getName(), newPwd);
	}

	@PostMapping("/save-expense")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public EdhaUser saveExpense(@RequestBody List<Expense> expenses, Principal user) throws Exception {
		return expenseService.saveTodaysExpense(user.getName(), expenses);
	}
	
	@GetMapping("/get-month-expense/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Expense> getMonthExpense(@PathVariable String username) throws Exception {
		return expenseService.getMonthExpense(username);
	}
}
