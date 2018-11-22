package com.edhaorganics.backend.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edhaorganics.backend.beans.Order;
import com.edhaorganics.backend.beans.OrderStatus;
import com.edhaorganics.backend.repo.OrderRepository;
import com.edhaorganics.backend.util.DateUtil;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;


	@Autowired
	private MailService mailService;

	@Transactional
	public Long placeNewOrder(Order order) {
		order.setStatus(OrderStatus.NEW);
		Order orderPlaced = orderRepo.save(order);
		return orderPlaced.getId();
	}

	public List<Order> getOrders() {
		return orderRepo.findTop200ByOrderByCreatedOnDesc();
	}

	public List<Order> getOrdersOfUser(String salesman) {
		return orderRepo.findByUser_Username(salesman);
	}

	public List<Order> getOrdersByStatus(String status) {
		return orderRepo.findTop100ByStatusOrderByCreatedOnDesc(OrderStatus.valueOf(status));
	}

	public List<Order> getOrdersByCutomerName(String custName) {
		return orderRepo.findByCustomer_CustomerNameContainingIgnoreCase(custName);
	}

	public List<Order> getOrdersByIncharge(String salesmanName) {
		return orderRepo.findByUser_FullNameContainingIgnoreCase(salesmanName);
	}

	public List<Order> getOrdersByFilter(String customerName, String period, String inchargeName) {
		if (!StringUtils.isEmpty(customerName) && !StringUtils.isEmpty(period) && !StringUtils.isEmpty(inchargeName)) {
			return orderRepo
					.findByCustomer_CustomerNameContainingIgnoreCaseAndUser_FullNameContainingIgnoreCaseAndCreatedOnBetween(
							customerName, inchargeName, DateUtil.getStartDate(period), DateUtil.getToDate(period));
		} else if (!StringUtils.isEmpty(customerName) && !StringUtils.isEmpty(inchargeName)) {
			return orderRepo.findByCustomer_CustomerNameContainingIgnoreCaseAndUser_FullNameContainingIgnoreCase(
					customerName, inchargeName);
		} else if (!StringUtils.isEmpty(customerName) && !StringUtils.isEmpty(period)) {
			return orderRepo.findByCustomer_CustomerNameContainingIgnoreCaseAndAndCreatedOnBetween(customerName,
					DateUtil.getStartDate(period), DateUtil.getToDate(period));
		} else if (!StringUtils.isEmpty(period) && !StringUtils.isEmpty(inchargeName)) {
			return orderRepo.findByCreatedOnBetweenAndUser_FullNameContainingIgnoreCase(DateUtil.getStartDate(period),
					DateUtil.getToDate(period), inchargeName);
		} else if (!StringUtils.isEmpty(customerName)) {
			return orderRepo.findByCustomer_CustomerNameContainingIgnoreCase(customerName);
		} else if (!StringUtils.isEmpty(inchargeName)) {
			return orderRepo.findByUser_FullNameContainingIgnoreCase(inchargeName);
		} else if (!StringUtils.isEmpty(period)) {
			return orderRepo.findByCreatedOnBetween(DateUtil.getStartDate(period), DateUtil.getToDate(period));
		}
		return Collections.emptyList();
	}

	public Order updateOrderStatus(String orderId, String newStatus) {
		Order orderFromdb = orderRepo.getOne(Long.parseLong(orderId));
		orderFromdb.setStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
		return orderRepo.save(orderFromdb);
	}

	public List<Order> getClosedOrdersofUser(String username) {
		return orderRepo
				.findTop100ByStatusAndUser_usernameOrderByCreatedOnDesc(OrderStatus.valueOf("CLOSED"), username);
	}

}