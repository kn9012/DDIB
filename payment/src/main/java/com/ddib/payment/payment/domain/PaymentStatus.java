package com.ddib.payment.payment.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 상태 (PAYMENT_COMPLETED: 결제완료 / REFUND_COMPLETED: 환불완료)")
public enum PaymentStatus {
    PAYMENT_COMPLETED, REFUND_COMPLETED
}
