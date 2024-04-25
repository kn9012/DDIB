package com.ddib.product.product.dto.request;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.ProductDetail;
import com.ddib.product.user.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {

    private String name;

    private int totalStock;

    private LocalDateTime eventStartDate;

    private LocalDateTime eventEndDate;

    private int price;

    private double discount;

    private String category;

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
