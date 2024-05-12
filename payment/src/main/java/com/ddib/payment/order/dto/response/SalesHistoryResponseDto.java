package com.ddib.payment.order.dto.response;

import com.ddib.payment.order.dto.SalesHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SalesHistoryResponseDto {

    @Schema(description = "상품번호")
    @NotNull
    private int productId;

    @Schema(description = "판매내역 목록")
    @NotNull
    private List<SalesHistory> salesHistoryList;

}
