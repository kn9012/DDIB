package com.ddib.notification.notification.controller;

import com.ddib.notification.notification.dto.request.FCMNotificationRequestDto;
import com.ddib.notification.notification.service.FCMNotificationService;
import com.ddib.notification.notification.service.NotificationService;
import com.ddib.notification.subscriptioncategory.dto.request.SubscriptionCategoryRequestDto;
import com.ddib.notification.subscriptioncategory.service.SubscriptionCategoryService;
import com.ddib.notification.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "알림 API")
public class NotificationController {
    private final FCMNotificationService fcmNotificationService;
    private final UserServiceImpl userService;
    private final SubscriptionCategoryService subscriptionCategoryService;
    private final NotificationService notificationService;

    @Operation(summary = "FCM 알림 전송 API")
    @PostMapping("/send")
    public String sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }

    @Operation(summary = "구독 알림 신청 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PutMapping("/subscribe/{userId}")
    public ResponseEntity<Void> applyNotification(@RequestBody List<SubscriptionCategoryRequestDto> categories, @PathVariable Integer userId) {
        try {
            userService.findByUserId(userId).updateSubscribed();
            subscriptionCategoryService.createSubscriptionCategory(categories, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "구독 알림 취소 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PutMapping("/subscribe/cancel/{userId}")
    public ResponseEntity<Void> cancelNotification(@PathVariable Integer userId) {
        try {
            userService.findByUserId(userId).updateNotSubscribed();
            subscriptionCategoryService.deleteSubscriptionCategory(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "알림 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{userId}")
    public ResponseEntity<?> notificationDetails(@PathVariable Integer userId) {
        try {
            return new ResponseEntity<>(notificationService.findNotificationList(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
