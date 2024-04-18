package com.ddib.product.product.dto.response;

import com.ddib.product.product.domain.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailResponseDto {

    private Long productDetailId;

    private String imageUrl;

    public static ProductDetailResponseDto from(ProductDetail productDetail){
        return ProductDetailResponseDto.builder()
                .imageUrl(productDetail.getImageUrl())
                .productDetailId(productDetail.getProductDetailId())
                .build();
    }
}
