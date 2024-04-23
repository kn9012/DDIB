package com.ddib.payment.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class KakaoReadyRequestDto {

    @Schema(description = "회원 식별키 (주문번호 생성 용도)")
    @NotNull
    private int userId;

    @Schema(description = "상품 식별키 (주문번호 생성 용도)")
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

}
