package com.ddib.notification.notification.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMDto {
    @Schema(description = "FCM Token")
    @Column(nullable = false)
    private String fcmToken;

    @Schema(description = "제목")
    @Column(nullable = false)
    private String title;

    @Schema(description = "제목")
    @Column(nullable = false)
    private String body;
}
