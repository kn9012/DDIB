package com.ddib.notification.notification.domain;

import lombok.Getter;

@Getter
public class NotificationText {
    /* 구독한 카테고리 상품 등록 됐을 떄 */
    public static final String CATEGORY_PRODUCT_REGISTRATION_FIRST_TITLE = "님이 구독한 ";
    public static final String CATEGORY_PRODUCT_REGISTRATION_SECOND_TITLE = " 카테고리에 새 상품이 등록됐습니다!";

    public static final String CATEGORY_PRODUCT_REGISTRATION_CONTENT = "띱에 구경하러 오시는건 어떨까요?";

    /* 관심 상품 등록 됐을때 : 1시간/24시간 전 */
    public static final String INTEREST_PRODUCT_REMAIN_TIME_FIRST_TITLE = "님이 관심 있는 ";
    public static final String INTEREST_PRODUCT_REMAIN_TIME_SECOND_TITLE = "의 타임딜이 ";
    public static final String INTEREST_PRODUCT_REMAIN_TIME_THIRD_TITLE = "시간 남았어요!";
    public static final String INTEREST_PRODUCT_REMAIN_TIME_FOURTH_TITLE = "하루 남았어요!";

    public static final String INTEREST_PRODUCT_REMAIN_TIME_FIRST_CONTENT = "시간 뒤에 띱에서 파격적인 가격으로 ";
    public static final String INTEREST_PRODUCT_REMAIN_TIME_SECOND_CONTENT = "을 구매할 수 있어요!";





}
