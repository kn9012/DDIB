package com.ddib.product.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {

    NONE("없음"),
    FASHION("패션"),
    BEAUTY("뷰티"),
    FOOD("식품"),
    APPLIANCE("가전"),
    SPORTS("스포츠"),
    LIVING("생활용품"),
    PET("반려용품"),
    TRAVEL("여행");

    private final String meaning;

    public static ProductCategory searchCategoryByKeyword(String keyword) {
        try {
            return ProductCategory.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            return ProductCategory.NONE;
        }
    }

}
