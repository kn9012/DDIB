package com.ddib.payment.product.domain;

import com.ddib.payment.seller.domain.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
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
    @JoinColumn(nullable = false)
    @ManyToOne
    private Seller seller;

    @Schema(description = "상품명")
    @Column(nullable = false)
    private String name;

    @Schema(description = "총 재고")
    @Column(nullable = false)
    private int totalStock;

    @Schema(description = "재고")
    @Column(nullable = false)
    private int stock;

    @Schema(description = "이벤트 시작일")
    @Column(nullable = false)
    private Timestamp eventStartDate;

    @Schema(description = "이벤트 종료일")
    @Column(nullable = false)
    private Timestamp eventEndDate;

    @Schema(description = "이벤트 시작시간")
    @Column(nullable = false)
    private int eventStartTime;

    @Schema(description = "이벤트 종료시간")
    @Column(nullable = false)
    private int eventEndTime;

    @Schema(description = "이벤트 종료 여부")
    @Column(nullable = false)
    private boolean isOver;

    @Schema(description = "원가")
    @Column(nullable = false)
    private int price;

    @Schema(description = "할인율")
    @Column(nullable = false)
    private double discount;

    @Schema(description = "상품 대표사진")
    @Column(nullable = false)
    private String thumbnailImage;

    public void updateStock(int currentStock, int quantity) {
        this.stock = currentStock - quantity;
//        this.stock -= quantity;
        log.info("stock : " + this.stock);
    }

    public void updateStockByRefund(int quantity) {
        this.stock += quantity;
        log.info("stock : " + this.stock);
    }

}
