package com.ddib.payment.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "결제 상세 정보 DTO")
@Getter
public class CardInfo {

    @Schema(description = "카카오페이 매입사명")
    private String kakaopay_purchase_corp;

    @Schema(description = "카카오페이 매입사 코드")
    private String kakaopay_purchase_corp_code;

    @Schema(description = "카카오페이 발급사명")
    private String kakaopay_issuer_corp;

    @Schema(description = "카카오페이 발급사 코드")
    private String kakaopay_issuer_corp_code;

    @Schema(description = "카드 BIN")
    private String bin;

    @Schema(description = "카드 타입")
    private String card_type;

    @Schema(description = "할부 개월 수")
    private String install_month;

    @Schema(description = "카드사 승인번호")
    private String approved_id;

    @Schema(description = "카드사 가맹점 번호")
    private String card_mid;

    @Schema(description = "무이자할부 여부(Y/N)")
    private String interest_free_install;

    @Schema(description = "할부 유형 - CARD_INSTALLMENT: 업종 무이자 / SHARE_INSTALLMENT: 분담 무이자")
    private String installment_type;

    @Schema(description = "카드 상품 코드")
    private String card_item_code;

}
