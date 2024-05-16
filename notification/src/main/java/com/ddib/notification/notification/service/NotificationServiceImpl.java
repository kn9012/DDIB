package com.ddib.notification.notification.service;

import com.ddib.notification.notification.domain.Notification;
import com.ddib.notification.notification.dto.request.FCMDto;
import com.ddib.notification.notification.dto.request.NotificationCreateDto;
import com.ddib.notification.notification.dto.response.NotificationDetailResponseDto;
import com.ddib.notification.notification.repository.NotificationRepository;
import com.ddib.notification.notification.util.FCMSender;
import com.ddib.notification.user.domain.User;
import com.ddib.notification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ddib.notification.notification.domain.NotificationText.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FCMSender fcmSender;

    public void createNotification(NotificationCreateDto dto) {
        User user = userRepository.findByUserId(dto.getUserId());
        String userName = user.getName();
        String title = "";
        String content = "";

        // 구독한 카테고리 새상품 등록 시
        if (dto.getDomain().equals("CATEGORY_PRODUCT_REGISTRATION")) {
            title += userName + CATEGORY_PRODUCT_REGISTRATION_FIRST_TITLE;
            title += dto.getCategory() + CATEGORY_PRODUCT_REGISTRATION_SECOND_TITLE;
            content = CATEGORY_PRODUCT_REGISTRATION_CONTENT;
        } else if (dto.getDomain().equals("INTEREST_PRODUCT_REMAIN_TIME")) {
            title += userName + INTEREST_PRODUCT_REMAIN_TIME_FIRST_TITLE;
            title += dto.getProductName() + INTEREST_PRODUCT_REMAIN_TIME_SECOND_TITLE;

            if (dto.getRemainTime() == 1) {
                title += dto.getRemainTime() + INTEREST_PRODUCT_REMAIN_TIME_THIRD_TITLE;
            } else if (dto.getRemainTime() == 24) {
                title += INTEREST_PRODUCT_REMAIN_TIME_FOURTH_TITLE;
            }

            content += dto.getRemainTime() + INTEREST_PRODUCT_REMAIN_TIME_FIRST_CONTENT;
            content += dto.getProductName() + INTEREST_PRODUCT_REMAIN_TIME_SECOND_CONTENT;
        }

        Notification notification = Notification.builder()
                .user(user)
                .domain(dto.getDomain())
                .title(title)
                .content(content)
                .generatedTime(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        if (user.getFcmToken() != null && !user.getFcmToken().isEmpty()) {
            FCMDto fcmDto = FCMDto.builder()
                    .fcmToken(user.getFcmToken())
                    .title(notification.getTitle())
                    .body(notification.getContent())
                    .build();
            fcmSender.sendFCM(fcmDto);
        }
    }
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
