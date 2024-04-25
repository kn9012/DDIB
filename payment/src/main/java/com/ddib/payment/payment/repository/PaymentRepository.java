package com.ddib.payment.payment.repository;

import com.ddib.payment.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    @Query("SELECT p FROM Payment p WHERE p.order.orderId = :orderId")
    Payment findByOrderId(String orderId);

}
