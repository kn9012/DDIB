package com.ddib.payment.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductStockDecreaseRequestDto {

    @Schema(description = "상품 식별키")
    @NotNull
    private int productId;

    @Schema(description = "구매량")
    @NotNull
    private int amount;

}
