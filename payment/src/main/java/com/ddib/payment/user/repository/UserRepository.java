package com.ddib.payment.user.repository;

import com.ddib.payment.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
