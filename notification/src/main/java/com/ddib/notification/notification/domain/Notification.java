package com.ddib.notification.notification.domain;

import com.ddib.notification.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "알림")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "알림 식별키")
    private Integer notificationId;

    @Schema(description = "알림 도메인")
    @Column(nullable = false)
    private String domain;

    @Schema(description = "제목")
    @Column(nullable = false)
    private String title;

    @Schema(description = "내용")
    @Column(nullable = false)
    private String content;

    @ColumnDefault(value = "false")
    @Schema(description = "읽음 여부")
    private boolean isRead;

    @Schema(description = "알림 생성 시각")
    private LocalDateTime generatedTime;

    @Schema(description = "일반회원")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
