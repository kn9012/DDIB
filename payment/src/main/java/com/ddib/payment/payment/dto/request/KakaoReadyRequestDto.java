package com.ddib.payment.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoReadyRequestDto {

    @Schema(description = "상품 식별키")
    @NotNull
    private int productId;

    @Schema(description = "상품명, 최대 100자")
    @NotNull
    private String itemName;

    @Schema(description = "상품 수량")
    @NotNull
    private int quantity;

    @Schema(description = "상품 총액")
    @NotNull
    private int totalAmount;

    @Schema(description = "상품 비과세 금액")
    @NotNull
    private int taxFreeAmount;

    @Schema(description = "배송 받는 사람 이름")
    @NotNull
    private String receiverName;

    @Schema(description = "배송 받는 사람 핸드폰 번호")
    @NotNull
    private String receiverPhone;

    @Schema(description = "주문 상품을 배송할 도로명 주소")
    @NotNull
    private String orderRoadAddress;

    @Schema(description = "주문 상품을 배송할 상세 주소")
    @NotNull
    private String orderDetailAddress;

    @Schema(description = "주문 상품을 배송할 우편번호")
    @NotNull
    private String orderZipcode;

}
