package com.ddib.payment.payment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KakaoReadyResponseDto {

    @Schema(description = "결제 고유번호")
    @NotNull
    private String tid;

    @Schema(description = "PC 웹일 경우 받는 결제 페이지 (QR코드 화면)")
    @NotNull
    private String next_redirect_pc_url;

    @Schema(description = "결제 준비 요청 시간")
    @NotNull
    private LocalDateTime created_at;

}
