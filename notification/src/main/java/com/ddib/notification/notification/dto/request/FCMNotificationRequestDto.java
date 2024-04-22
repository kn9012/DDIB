package com.ddib.notification.notification.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMNotificationRequestDto {
    private Integer targetUserId;
    private String title;
    private String content;
//    private String image;
//    private Map<String, String> data;
}
