package com.ddib.payment.payment.dto.response;

import lombok.Getter;

@Getter
public class KakaoReadyResponseDto {

    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // PC 웹일 경우 받는 결제 페이지 (QR코드 화면)
    private String created_at; // 결제 요청 생성 시간

}
