package com.ddib.payment.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "결제 금액 정보 DTO")
@Getter
public class Amount {

    @Schema(description = "전체 결제 금액")
    @NotNull
    private int total;

    @Schema(description = "비과세 금액")
    private int tax_free;

    @Schema(description = "부가세 금액")
    private int vat;

    @Schema(description = "할인 금액")
    private int discount;

}
