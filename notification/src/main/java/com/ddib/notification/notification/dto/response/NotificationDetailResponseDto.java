package com.ddib.notification.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "일반회원 알림 조회 응답 DTO")
public class NotificationDetailResponseDto {
    @Schema(description = "제목")
    @Column(nullable = false)
    private String title;

    @Schema(description = "내용")
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Schema(description = "읽음 여부")
    private boolean isRead;

    @ColumnDefault(value = "false")
    @Schema(description = "알림 생성 시각")
    private LocalDateTime generatedTime;
}
