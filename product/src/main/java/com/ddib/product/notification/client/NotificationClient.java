package com.ddib.product.notification.client;

import com.ddib.product.notification.dto.request.NotificationCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="notification-local", url="http://localhost:8084")
public interface NotificationClient {

    @PostMapping("/api/notification/create")
    public void createAlarm(@RequestBody NotificationCreateDto dto);

    @GetMapping("/api/notification/{userId}")
    public ResponseEntity<?> notificationDetails(@PathVariable Integer userId);
}
