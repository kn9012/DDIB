package com.ddib.payment.product.domain;

import com.ddib.payment.seller.domain.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Schema(description = "상품 식별키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Schema(description = "판매회원 정보")
    @ManyToOne
    private Seller seller;

    @Schema(description = "카테고리")
    private boolean category;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "총 재고")
    private int totalStock;

    @Schema(description = "재고")
    private int stock;

    @Schema(description = "이벤트일")
    private Timestamp eventDate;

    @Schema(description = "이벤트 시작시간")
    private int eventStartTime;

    @Schema(description = "이벤트 종료시간")
    private int eventEndTime;

    @Schema(description = "이벤트 종료 여부")
    private boolean isOver;

    @Schema(description = "원가")
    private int price;

    @Schema(description = "할인율")
    private double discount;

    @Schema(description = "상품 대표사진")
    private String thumbnailImage;

    @Schema(description = "결제 필요 여부")
    private boolean isNeedPayment;

}
