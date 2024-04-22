package com.ddib.notification.notification.repository;

import com.ddib.notification.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
