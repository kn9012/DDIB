package com.ddib.payment.payment.domain;

import com.ddib.payment.order.domain.Order;
import com.ddib.payment.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Schema(description = "결제 번호")
    @Id
    private String tid;

    @Schema(description = "결제 총 금액")
    @Column(nullable = false)
    private int totalAmount;

    @Schema(description = "결제 비과세 금액")
    @Column(nullable = false)
    private int taxFree;

    @Schema(description = "결제 수단 (CARD or MONEY)")
    @Column(nullable = false)
    private String paymentMethodType;

    @Schema(description = "결제 시간 (결제 승인 시각)")
    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Schema(description = "결제자")
    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    @Schema(description = "주문 정보")
    @JoinColumn(nullable = false)
    @OneToOne
    private Order order;

    @Schema(description = "결제 상태 (결제완료 / 환불완료)")
    @Column(nullable = false)
    private PaymentStatus status;

    @Schema(description = "환불 시각")
    private LocalDateTime refundedAt;

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    public void updateRefundedAt(LocalDateTime refundedAt) {
        this.refundedAt = refundedAt;
    }

}
