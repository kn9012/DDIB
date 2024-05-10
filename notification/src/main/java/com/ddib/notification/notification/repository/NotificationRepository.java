package com.ddib.notification.notification.repository;

import com.ddib.notification.notification.domain.Notification;
import com.ddib.notification.notification.dto.response.NotificationDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<NotificationDetailResponseDto> findAllByUserUserId(Integer userId);
}
