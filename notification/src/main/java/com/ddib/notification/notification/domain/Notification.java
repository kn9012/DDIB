package com.ddib.notification.notification.domain;

import com.ddib.notification.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;

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

    @Schema(description = "제목")
    @Column(nullable = false)
    private String title;

    @Schema(description = "내용")
    @Column(nullable = false)
    private String content;

    @ColumnDefault(value = "false")
    @Column(nullable = false)
    @Schema(description = "읽음 여부")
    private boolean isRead;
}
