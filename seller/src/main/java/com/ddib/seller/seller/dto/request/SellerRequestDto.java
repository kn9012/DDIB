package com.ddib.seller.seller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "판매회원 요청 DTO")
public class SellerRequestDto {
    @Schema(description = "기업명")
    private String companyName;

    @Schema(description = "사업자 등록 번호")
    private int businessNumber;

    @Schema(description = "대표명")
    private String ceoName;

    @Schema(description = "대표 이메일")
    private String ceoEmail;

    @Schema(description = "대표 전화번호")
    private String ceoPhone;
}
