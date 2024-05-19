package com.ddib.payment.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {

    @Schema(description = "주문번호")
    @NotNull
    private String orderId;

    @Schema(description = "주문 날짜")
    @NotNull
    private String orderDate;

    @Schema(description = "주문 상태")
    @NotNull
    private int status;

    @Schema(description = "기업명")
    @NotNull
    private String companyName;

    @Schema(description = "상품 썸네일 이미지")
    @NotNull
    private String thumbnailImage;

    @Schema(description = "상품 식별키")
    @NotNull
    private int productId;

    @Schema(description = "상품명")
    @NotNull
    private String productName;

    @Schema(description = "구매 수량")
    @NotNull
    private int quantity;

    @Schema(description = "상품 원가")
    @NotNull
    private int price;

    @Schema(description = "총 결제 금액")
    @NotNull
    private int totalAmount;

    @Schema(description = "배송 받는 사람 이름")
    private String receiverName;

    @Schema(description = "배송 받는 사람 핸드폰 번호")
    private String receiverPhone;

    @Schema(description = "배송받을 도로명 주소")
    private String orderRoadAddress;

    @Schema(description = "배송받을 상세 주소")
    private String orderDetailAddress;

    @Schema(description = "배송받을 우편번호")
    private String orderZipcode;

    @Schema(description = "결제수단")
    private String paymentMethod;

}
