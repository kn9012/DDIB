package com.ddib.payment.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "이번 요청으로 취소된 금액 정보 DTO")
@Getter
public class ApprovedCancelAmount {

    @Schema(description = "이번 요청으로 취소된 전체 결제 금액")
    private int total;

    @Schema(description = "이번 요청으로 취소된 비과세 금액")
    private int tax_free;

    @Schema(description = "이번 요청으로 취소된 부가세 금액")
    private int vat;

    @Schema(description = "이번 요청으로 취소된 사용한 포인트 금액")
    private int point;

    @Schema(description = "이번 요청으로 취소된 할인 금액")
    private int discount;

    @Schema(description = "이번 요청으로 취소된 컵 보증금")
    private int green_deposit;

}
