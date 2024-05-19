package com.ddib.payment.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalesHistory {

    @Schema(description = "주문번호")
    @NotNull
    private String orderId;

    @Schema(description = "주문자")
    @NotNull
    private String name;

    @Schema(description = "주문한 사람의 핸드폰 번호")
    @NotNull
    private String phone;

    @Schema(description = "배송지")
    @NotNull
    private String address;

}
