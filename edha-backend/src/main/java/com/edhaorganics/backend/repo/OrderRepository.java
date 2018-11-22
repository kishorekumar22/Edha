package com.edhaorganics.backend.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edhaorganics.backend.beans.Order;
import com.edhaorganics.backend.beans.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findTop100ByStatusOrderByCreatedOnDesc(OrderStatus status);

	List<Order> findByCustomer_CustomerNameContainingIgnoreCase(String custName);

	List<Order> findByUser_FullNameContainingIgnoreCase(String salesmanName);

	List<Order> findByCustomer_CustomerNameContainingIgnoreCaseAndUser_FullNameContainingIgnoreCase(
			String customerName, String inchargeName);

	List<Order> findByCustomer_CustomerNameContainingIgnoreCaseAndUser_FullNameContainingIgnoreCaseAndCreatedOnBetween(
			String customerName, String inchargeName, LocalDateTime date, LocalDateTime toDate);

	List<Order> findByCustomer_CustomerNameContainingIgnoreCaseAndAndCreatedOnBetween(String customerName,
			LocalDateTime startDate, LocalDateTime toDate);

	List<Order> findByCreatedOnBetweenAndUser_FullNameContainingIgnoreCase(LocalDateTime startDate,
			LocalDateTime toDate, String inchargeName);

	List<Order> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime toDate);

	List<Order> findTop200ByOrderByCreatedOnDesc();

	List<Order> findByUser_Username(String salesman);

	List<Order> findTop100ByStatusAndUser_usernameOrderByCreatedOnDesc(OrderStatus status, String username);

//	@Query(value = "select o from Order o join fetch o.customer left join fetch o.user where o.id=?1")
//	Order findByOrderId(Long id);
	
	@Override
	@EntityGraph(attributePaths = "products")
	@Query("FROM Order order")
	Order getOne(Long id);

}
