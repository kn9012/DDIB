package com.ddib.notification.notification.controller;

import com.ddib.notification.notification.dto.request.NotificationCreateDto;
import com.ddib.notification.notification.service.NotificationService;
import com.ddib.notification.subscriptioncategory.dto.request.SubscriptionCategoryRequestDto;
import com.ddib.notification.subscriptioncategory.service.SubscriptionCategoryService;
import com.ddib.notification.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "알림 API")
public class NotificationController {
    private final UserServiceImpl userService;
    private final SubscriptionCategoryService subscriptionCategoryService;
    private final NotificationService notificationService;

    @Operation(summary = "FCM 알림 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FCM 알림 생성 성공"),
            @ApiResponse(responseCode = "400", description = "FCM 알림 생성 실패")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createAlarm(@RequestBody NotificationCreateDto dto) {
        try {
            notificationService.createNotification(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "구독 알림 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구독 알림 신청 성공"),
            @ApiResponse(responseCode = "400", description = "구독 알림 신청 실패")
    })
    @PutMapping("/subscribe/{userId}")
    public ResponseEntity<?> applyNotification(@RequestBody SubscriptionCategoryRequestDto categories, @PathVariable Integer userId) {
        try {
            userService.findByUserId(userId).updateSubscribed();
            subscriptionCategoryService.createSubscriptionCategory(categories, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "구독 알림 취소 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구독 알림 취소 성공"),
            @ApiResponse(responseCode = "400", description = "구독 알림 취소 실패")
    })
    @PutMapping("/subscribe/cancel/{userId}")
    public ResponseEntity<?> cancelNotification(@PathVariable Integer userId) {
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "알림 목록 조회 실패")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> notificationDetails(@PathVariable Integer userId) {
        try {
            return new ResponseEntity<>(notificationService.findNotificationList(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
