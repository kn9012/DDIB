package com.ddib.product.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {

    NONE("없음", "None"),
    FASHION("패션", "Fashion"),
    BEAUTY("뷰티", "Beauty"),
    FOOD("식품", "Food"),
    APPLIANCE("가전", "Appliance"),
    SPORTS("스포츠", "Sports"),
    LIVING("생활용품", "Living"),
    PET("반려용품", "Pet"),
    TRAVEL("여행", "Travel");

    private final String meaning;

    private final String value;

    public static ProductCategory searchCategoryByKeyword(String keyword) {
        try {
            return ProductCategory.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            return ProductCategory.NONE;
        }
    }

}
