package com.ddib.notification.user.repository;

import com.ddib.notification.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);
}
