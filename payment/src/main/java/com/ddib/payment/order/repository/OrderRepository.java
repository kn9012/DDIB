package com.ddib.payment.order.repository;

import com.ddib.payment.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderId(String orderId);

    void deleteByOrderId(String orderId);

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId ORDER BY o.orderDate DESC")
    List<Order> findAllByUserId(int userId);

}
