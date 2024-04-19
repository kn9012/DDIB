package com.ddib.notification.user.domain;

import com.ddib.notification.notification.domain.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "일반회원")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "일반회원")
    private Integer userId;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "핸드폰 번호")
    private String phone;

    @Schema(description = "도로명 주소")
    private String roadAddress;

    @Schema(description = "상세 주소")
    private String detailAddress;

    @Schema(description = "우편번호")
    private int zipcode;

    @Schema(description = "회원 분류")
    private String userType;

    @Schema(description = "구독 여부")
    private boolean isSubscribed;

    @Schema(description = "FCM 토큰")
    private String fcmToken;

    @Schema(description = "알림")
    @OneToMany
    private List<Notification> notifications;
}
