package com.ddib.seller.seller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일반회원 정보 DTO")
public class UserInfoDto {
    @NotNull
    @Schema(description = "이름")
    private String name;

    @NotNull
    @Schema(description = "이메일")
    private String email;

    @NotNull
    @Schema(description = "핸드폰 번호")
    private String phone;

    @NotNull
    @Schema(description = "도로명 주소")
    private String roadAddress;

    @NotNull
    @Schema(description = "상세 주소")
    private String detailAddress;

    @NotNull
    @Schema(description = "우편번호")
    private int zipcode;
}
