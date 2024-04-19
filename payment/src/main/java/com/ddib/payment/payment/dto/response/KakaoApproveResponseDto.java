package com.ddib.payment.payment.dto.response;

import com.ddib.payment.payment.dto.Amount;
import com.ddib.payment.payment.dto.CardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class KakaoApproveResponseDto {

    @Schema(description = "요청 고유 번호 - 승인/취소가 구분된 결제번호")
    @NotNull
    private String aid;

    @Schema(description = "결제 고유 번호 - 승인/취소가 동일한 결제번호")
    @NotNull
    private String tid;

    @Schema(description = "가맹점 코드")
    @NotNull
    private String cid;

    @Schema(description = "가맹점 주문번호 (최대 100자)")
    @NotNull
    private String partnerOrderId;

    @Schema(description = "가맹점 회원 ID (최대 100자)")
    @NotNull
    private String partnerUserId;

    @Schema(description = "결제 수단 (CARD or MONEY)")
    @NotNull
    private String paymentMethodType;

    @Schema(description = "결제 금액 정보")
    @NotNull
    private Amount amount;

    @Schema(description = "결제 상세 정보 (결제 수단이 카드일 경우만)")
    private CardInfo cardInfo;

    @Schema(description = "상품 이름 (최대 100자)")
    @NotNull
    private String itemName;

    @Schema(description = "상품 코드 (최대 100자)")
    @NotNull
    private String itemCode;

    @Schema(description = "상품 수량")
    @NotNull
    private int quantity;

    @Schema(description = "결제 준비 요청 시각")
    @NotNull
    private String createdAt;

    @Schema(description = "결제 승인 시각")
    @NotNull
    private String approvedAt;

    @Schema(description = "결제 승인 요청에 대해 저장한 값, 요청 시 전달된 내용")
    @NotNull
    private String payload;

}
