package com.ddib.product.product.dto.request;

import lombok.Getter;

@Getter
public class ProductStockUpdateRequestDto {

    private int productId;

    private int amount;

}
