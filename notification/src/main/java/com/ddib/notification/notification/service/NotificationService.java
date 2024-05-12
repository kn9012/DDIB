package com.ddib.notification.notification.service;

import com.ddib.notification.notification.dto.request.NotificationCreateDto;
import com.ddib.notification.notification.dto.response.NotificationDetailResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    List<NotificationDetailResponseDto> findNotificationList(Integer userId);
    void createNotification(NotificationCreateDto dto);
}
