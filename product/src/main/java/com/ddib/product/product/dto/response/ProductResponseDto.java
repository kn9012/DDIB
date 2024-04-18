package com.ddib.product.product.dto.response;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.ProductDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private Integer productId;

    private String name;

    private int totalStock;

    private int stock;

    private Timestamp eventDate;

    private int eventStartTime;

    private int eventEndTime;

    private int price;

    private double discount;

    private String thumbnailImage;

    private ProductCategory category;

    private List<ProductDetailResponseDto> details;

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .totalStock(product.getTotalStock())
                .stock(product.getStock())
                .eventDate(product.getEventDate())
                .eventStartTime(product.getEventStartTime())
                .eventEndTime(product.getEventEndTime())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .thumbnailImage(product.getThumbnailImage())
                .category(product.getCategory())
                .details(product.getDetails()
                        .stream()
                        .map(ProductDetailResponseDto::from)
                        .toList()) // dto 변환 필요
                .build();
    }
}
