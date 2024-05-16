package com.ddib.notification.notification.util;

import com.ddib.notification.notification.dto.request.FCMDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FCMSender {

    private final FirebaseMessaging firebaseMessaging;

    public void sendFCM(FCMDto fcmDto) {
        Notification notification = Notification.builder()
                .setTitle(fcmDto.getTitle())
                .setBody(fcmDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(fcmDto.getFcmToken())
                .setNotification(notification)
                .build();

        try {
            log.info("FCM 알람 전송 : {}", fcmDto.getFcmToken());
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.info("FCM 알람 에러 : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}