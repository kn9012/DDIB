package com.ddib.product.notification.domain;

import com.ddib.product.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "구독 카테고리")
public class SubscriptionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "구독 카테고리 식별키")
    private Integer subscriptionCategoryId;

    @Schema(description = "회원 식별키")
    @ManyToOne
    private User user;

    @Schema(description = "구독 카테고리")
    private String subscriptionCategory;
}
