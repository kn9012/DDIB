package com.ddib.product.product.client;

import com.ddib.product.product.dto.NotificationCreateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.cloud.openfeign.FeignClient(name = "notification", url = "${feign-client.notification}/api/notification")
public interface NotificationClient {
    @PostMapping("/create")
    public void createNotification(@RequestBody NotificationCreateDto dto);
}
