package com.ddib.product.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductMainResponseDto {

    private List<ProductResponseDto> todayNotOverProducts;

    private List<ProductResponseDto> todayProducts;
}
