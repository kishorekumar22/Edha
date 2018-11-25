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

import com.edhaorganics.backend.beans.Order;
import com.edhaorganics.backend.beans.Payment;
import com.edhaorganics.backend.service.MailService;
import com.edhaorganics.backend.service.OrderService;

@RestController
@RequestMapping("/edha/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private MailService mailService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public Long placeOrder(@RequestBody @Valid Order order, Principal user) {
		Long orderid = orderService.placeNewOrder(order, user);
		mailService.sendOrderConfirmation(orderid);
		return orderid;

	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Order> list() {
		return orderService.getOrders();
	}

	@GetMapping("/list-by-status/{status}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Order> listOrdersByStatus(@PathVariable String status) {
		return orderService.getOrdersByStatus(status);
	}

	@GetMapping("/list-my-orders/{salesman}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Order> listOrdersOfSalesman(@PathVariable String salesman) {
		return orderService.getOrdersOfUser(salesman);
	}

	@GetMapping("/list-orders-by-filter/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Order> listOrdersInPeriod(@RequestParam(required = false) String customerName,
			@RequestParam(required = false) String period, @RequestParam(required = false) String inchargeName) {
		return orderService.getOrdersByFilter(customerName, period, inchargeName);
	}

	@GetMapping("/update-order-status/{orderId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public Order updateStatus(@PathVariable String orderId, @RequestParam(required = true) String newStatus) {
		return orderService.updateOrderStatus(orderId, newStatus);
	}

	@GetMapping("/list-my-closed-orders/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public List<Order> listClosedOrderOfSaleman(@PathVariable String username) {
		return orderService.getClosedOrdersofUser(username);
	}

	@PostMapping("/add-payment-to-order/{orderId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMAN')")
	public Order addPaymentToOrder(@PathVariable String orderId, @RequestBody Payment payment) {
		Order order = orderService.addPaymentToOrder(orderId, payment);
		mailService.sendPaymentUpdate(order.getId());
		return order;
	}
}
