package com.ddib.payment.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 상태 (: 결제완료 / : 취소완료)")
public enum OrderStatus {
    PAYMENT_COMPLETED, CANCELED
}
