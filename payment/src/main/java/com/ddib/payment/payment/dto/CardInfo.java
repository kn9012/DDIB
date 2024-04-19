package com.ddib.payment.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "결제 상세 정보 DTO")
@Getter
public class CardInfo {

    @Schema(description = "카카오페이 매입사명")
    private String kakaopayPurchaseCorp;

    @Schema(description = "카카오페이 매입사 코드")
    private String kakaopayPurchaseCorpCode;

    @Schema(description = "카카오페이 발급사명")
    private String kakaopayIssuerCorp;

    @Schema(description = "카카오페이 발급사 코드")
    private String kakaopayIssuerCorpCode;

    @Schema(description = "카드 BIN")
    private String bin;

    @Schema(description = "카드 타입")
    private String cardType;

    @Schema(description = "할부 개월 수")
    private String installMonth;

    @Schema(description = "카드사 승인번호")
    private String approvedId;

    @Schema(description = "카드사 가맹점 번호")
    private String cardMid;

    @Schema(description = "무이자할부 여부(Y/N)")
    private String interestFreeInstall;

    @Schema(description = "할부 유형 - CARD_INSTALLMENT: 업종 무이자 / SHARE_INSTALLMENT: 분담 무이자")
    private String installmentType;

    @Schema(description = "카드 상품 코드")
    private String cardItemCode;

}
