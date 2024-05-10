package com.ddib.notification.notification.service;

import com.ddib.notification.notification.dto.response.NotificationDetailResponseDto;
import com.ddib.notification.notification.repository.NotificationRepository;
import com.ddib.notification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    @Override
    public List<NotificationDetailResponseDto> findNotificationList(Integer userId) {
        return notificationRepository.findAllByUserUserId(userId);
    }
}
