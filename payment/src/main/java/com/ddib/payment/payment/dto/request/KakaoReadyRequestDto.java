package com.ddib.payment.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class KakaoReadyRequestDto {

//    @Schema(description = "가맹점 코드, 10자")
//    @NotNull
//    private String cid;

    @Schema(description = "가맹점 주문번호, 최대 100자")
    @NotNull
    private String partnerOrderId;

    @Schema(description = "가맹점 회원 id, 최대 100자")
    @NotNull
    private String partnerUserId;

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

    @Schema(description = "결제 성공 시 redirect url, 최대 255자")
    @NotNull
    private String approvalUrl;

    @Schema(description = "결제 취소 시 redirect url, 최대 255자")
    @NotNull
    private String cancelUrl;

    @Schema(description = "결제 실패 시 redirect url, 최대 255자")
    @NotNull
    private String failUrl;

}
