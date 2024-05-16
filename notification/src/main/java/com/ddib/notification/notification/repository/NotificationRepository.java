package com.ddib.notification.notification.repository;

import com.ddib.notification.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("select n from Notification n where n.user.userId = :userId order by n.generatedTime desc")
    List<Notification> findAllByUserUserId(@Param("userId") Integer userId);
}
