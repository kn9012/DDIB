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
public class Order {

    @Schema(description = "주문 식별키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    @Schema(description = "회원 정보")
    @ManyToOne
    private User user;

    @Schema(description = "상품 정보")
    @ManyToOne
    private Product product;

    @Schema(description = "주문일")
    private Timestamp orderDate;

    @Schema(description = "상품 개수")
    private int productCount;

    @Schema(description = "총 금액")
    private int totalPrice;

    @Schema(description = "배송할 도로명 주소")
    private String orderRoadAddress;

    @Schema(description = "배송할 상세 주소")
    private String orderDetailAddress;

    @Schema(description = "배송할 우편번호")
    private int orderZipcode;

}
