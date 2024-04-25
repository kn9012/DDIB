package com.ddib.product.product.dto.response;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewResponseDto {

    private Integer productId;

    private String name;

    private int totalStock;

    private int stock;

    private Timestamp eventStartDate;

    private Timestamp eventEndDate;

    private int eventStartTime;

    private int eventEndTime;

    private int price;

    private double discount;

    private String thumbnailImage;

    private String category;

    private List<ProductDetailResponseDto> details;

    private int likeCount;

    private Integer sellerId;

    private String companyName;

    private long businessNumber;

    private long companyPhone;

    private String companyEmail;

    private boolean isLiked;

    private boolean isOver;

    public static ProductViewResponseDto of(Product product, boolean isLiked) {
        return ProductViewResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .totalStock(product.getTotalStock())
                .stock(product.getStock())
                .eventStartDate(product.getEventStartDate())
                .eventEndDate(product.getEventEndDate())
                .eventStartTime(product.getEventStartTime())
                .eventEndTime(product.getEventEndTime())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .thumbnailImage(product.getThumbnailImage())
                .category(product.getCategory().getValue())
                .details(product.getDetails()
                        .stream()
                        .map(ProductDetailResponseDto::from)
                        .toList()) // dto 변환 필요
                .likeCount(product.getLikedUsers().size())
                .sellerId(product.getSeller().getSellerId())
                .companyName(product.getSeller().getCompanyName())
                .businessNumber(product.getSeller().getBusinessNumber())
                .companyPhone(product.getSeller().getCompanyPhone())
                .companyEmail(product.getSeller().getCompanyEmail())
                .isLiked(isLiked)
                .isOver(product.isOver())
                .build();
    }
}
