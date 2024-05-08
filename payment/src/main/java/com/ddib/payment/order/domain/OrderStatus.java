package com.ddib.payment.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "주문 상태 (: 결제완료 / : 취소완료)")
@Getter
public enum OrderStatus {
    PAYMENT_COMPLETED(0), CANCELED(1);

    private final int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
