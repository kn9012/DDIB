package com.ddib.product.product.dto.request;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.user.domain.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {

    @Schema(description = "상품명", defaultValue = "name")
    private String name;

    @Schema(description = "총 재고량", defaultValue = "1000")
    private int totalStock;

    @Schema(description = "타임딜 시작시간", defaultValue = "2024-04-25T10:00:00")
    private LocalDateTime eventStartDate;

    @Schema(description = "타임딜 종료시간", defaultValue = "2024-04-25T18:00:00")
    private LocalDateTime eventEndDate;

    @Schema(description = "가격", defaultValue = "20000")
    private int price;

    @Schema(description = "할인율", defaultValue = "20")
    private double discount;

    @Schema(description = "상품 카테고리", defaultValue = "fashion",
            allowableValues = {"Fashion", "Beauty", "Food", "Appliance", "Sports", "Living", "Pet", "Travel"})
    private String category;

    @Schema(description = "판매자 ID", defaultValue = "1")
    private int sellerId;

    public Product toEntity(Seller seller) {
        return Product.builder()
                .name(getName())
                .price(getPrice())
                .stock(getTotalStock())
                .totalStock(getTotalStock())
                .discount(getDiscount())
                .eventStartDate(Timestamp.valueOf(eventStartDate))
                .eventEndDate(Timestamp.valueOf(eventEndDate))
                .eventStartTime(eventStartDate.getHour())
                .eventEndTime(eventEndDate.getHour())
                .isOver(false)
                .seller(seller)
                .category(ProductCategory.valueOf(category))
                .build();
    }

}
