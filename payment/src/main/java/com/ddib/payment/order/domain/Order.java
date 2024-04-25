package com.ddib.payment.order.domain;

import com.ddib.payment.product.domain.Product;
import com.ddib.payment.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Schema(description = "주문 식별키")
    @Id
    private String orderId;

    @Schema(description = "주문한 회원 정보")
    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    @Schema(description = "상품 정보")
    @JoinColumn(nullable = false)
    @ManyToOne
    private Product product;

    @Schema(description = "주문일")
    @Column(nullable = false)
    private Timestamp orderDate;

    @Schema(description = "상품 개수")
    @Column(nullable = false)
    private int productCount;

    @Schema(description = "총 금액")
    @Column(nullable = false)
    private int totalPrice;

    @Schema(description = "배송 받는 사람 이름")
    @Column(nullable = false)
    private String receiverName;

    @Schema(description = "배송 받는 사람 핸드폰 번호")
    @Column(nullable = false)
    private String receiverPhone;

    @Schema(description = "배송할 도로명 주소")
    @Column(nullable = false)
    private String orderRoadAddress;

    @Schema(description = "배송할 상세 주소")
    @Column(nullable = false)
    private String orderDetailAddress;

    @Schema(description = "배송할 우편번호")
    @Column(nullable = false)
    private String orderZipcode;

    @Schema(description = "주문 상태")
    @Column(nullable = false)
    private OrderStatus status;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

}
