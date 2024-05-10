package com.ddib.notification.user.repository;

import com.ddib.notification.notification.dto.response.NotificationDetailResponseDto;
import com.ddib.notification.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);
}
