package com.ddib.notification.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCreateDto {
    @Schema(description = "회원 식별키")
    @NotNull
    private Integer userId;

    @NotNull
    @Schema(description = "알림 도메인")
    private String domain;

    @Schema(description = "카테고리")
    private String category;

    @Schema(description = "상품 이름")
    private String productName;

    @Schema(description = "남은 시간")
    private int remainTime;
}
