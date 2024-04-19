package com.ddib.payment.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class KakaoApproveRequestDto {

    @Schema(description = "가맹점 코드")
    @NotNull
    private String cid;

    @Schema(description = "결제 고유번호")
    @NotNull
    private String tid;

    @Schema(description = "가맹점 주문번호")
    @NotNull
    private String partnerOrderId;

    @Schema(description = "가맹점 회원 ID")
    @NotNull
    private String partnerUserId;

    @Schema(description = "결제승인 요청을 인증하는 토큰. 사용자 결제 수단 선택 완료 시, approval_url로 redirection 해줄 때 pg_token을 query string으로 전달")
    @NotNull
    private String pgToken;

}
