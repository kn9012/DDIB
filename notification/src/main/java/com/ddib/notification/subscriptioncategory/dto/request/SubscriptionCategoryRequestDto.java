package com.ddib.notification.subscriptioncategory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "구독 카테고리 요청 DTO")
public class SubscriptionCategoryRequestDto {
    @Schema(description = "패션 카테고리 구독 여부")
    private boolean subscribeFashion;

    @Schema(description = "뷰티 카테고리 구독 여부")
    private boolean subscribeBeauty;

    @Schema(description = "식품 카테고리 구독 여부")
    private boolean subscribeFood;

    @Schema(description = "가전제품 카테고리 구독 여부")
    private boolean subscribeAppliance;

    @Schema(description = "스포츠 카테고리 구독 여부")
    private boolean subscribeSports;

    @Schema(description = "생활 카테고리 구독 여부")
    private boolean subscribeLiving;

    @Schema(description = "반려동물 카테고리 구독 여부")
    private boolean subscribePet;

    @Schema(description = "여행 카테고리 구독 여부")
    private boolean subscribeTravel;
}
