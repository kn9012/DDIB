package com.ddib.notification.notification.service;

import com.ddib.notification.notification.domain.Notification;
import com.ddib.notification.notification.dto.response.NotificationDetailResponseDto;
import com.ddib.notification.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    @Override
    public List<NotificationDetailResponseDto> findNotificationList(Integer userId) {
        List<Notification> notifications = notificationRepository.findAllByUserUserId(userId);
        List<NotificationDetailResponseDto> responseDtos = new ArrayList<>();

        for (Notification n : notifications) {
            NotificationDetailResponseDto notificationDetailResponseDto = NotificationDetailResponseDto.builder()
                    .title(n.getTitle())
                    .content(n.getContent())
                    .isRead(n.isRead())
                    .generatedTime(n.getGeneratedTime())
                    .build();

            responseDtos.add(notificationDetailResponseDto);
        }

        return responseDtos;
    }
}
