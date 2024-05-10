package com.ddib.seller.seller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "판매회원 정보 DTO")
public class SellerInfoDto {
    @NotNull
    @Schema(description = "판매회원 식별키")
    private Integer sellerId;

    @NotNull
    @Schema(description = "기업명")
    private String companyName;

    @NotNull
    @Schema(description = "사업자 등록 번호")
    private String businessNumber;

    @NotNull
    @Schema(description = "대표명")
    private String ceoName;

    @NotNull
    @Schema(description = "대표 이메일")
    private String ceoEmail;

    @NotNull
    @Schema(description = "대표 전화번호")
    private String ceoPhone;
}
