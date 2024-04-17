package com.ddib.product.product.dto.request;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private ProductCategory category;

    public Product toEntity(String thumbnail, List<ProductDetail> details) {
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
                .build();

    }
}
