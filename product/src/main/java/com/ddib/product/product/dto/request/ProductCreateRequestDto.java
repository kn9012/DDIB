package com.ddib.product.product.dto.request;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.ProductDetail;
import com.ddib.product.user.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {

    private String name;

    private int totalStock;

    private Timestamp eventDate;

    private int eventStartTime;

    private int eventEndTime;

    private int price;

    private double discount;

    private String category;

    private int sellerId;

    public Product toEntity(String thumbnail, List<ProductDetail> details, Seller seller) {
        return Product.builder()
                .name(getName())
                .price(getPrice())
                .stock(getTotalStock())
                .totalStock(getTotalStock())
                .discount(getDiscount())
                .eventDate(getEventDate())
                .eventStartTime(getEventStartTime())
                .eventEndTime(getEventEndTime())
                .details(details)
                .thumbnailImage(thumbnail)
                .isOver(false)
                .category(ProductCategory.valueOf(category))
                .build();
    }

}
