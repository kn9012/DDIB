package com.ddib.notification.notification.controller;

import com.ddib.notification.notification.dto.request.FCMNotificationRequestDto;
import com.ddib.notification.notification.service.FCMNotificationService;
import com.ddib.notification.subscriptioncategory.dto.request.SubscriptionCategoryRequestDto;
import com.ddib.notification.subscriptioncategory.service.SubscriptionCategoryService;
import com.ddib.notification.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final UserService userService;
    private final SubscriptionCategoryService subscriptionCategoryService;

    @Operation(summary = "FCM 알림 전송 API")
    @PostMapping("/send")
    public String sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }

    @Operation(summary = "구독 알림 신청 API")
    @ApiResponse(responseCode = "200", description = "성공")
        @PutMapping("/subscribe/{userId}")
    public ResponseEntity<Void> applyNotification(@RequestBody List<SubscriptionCategoryRequestDto> categories, @PathVariable Integer userId) {
        userService.findByUserId(userId).updateSubscribed();
        subscriptionCategoryService.createSubscriptionCategory(categories, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "구독 알림 취소 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PutMapping("/subscribe/cancel/{userId}")
    public ResponseEntity<Void> cancelNotification(@PathVariable Integer userId) {
        userService.findByUserId(userId).updateNotSubscribed();
        subscriptionCategoryService.deleteSubscriptionCategory(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
