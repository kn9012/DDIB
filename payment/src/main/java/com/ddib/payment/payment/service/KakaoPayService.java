package com.ddib.payment.payment.service;

import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    @Value("${pay.kakao.cid}")
    private String cid;
    @Value("${pay.kakao.secret-key}")
    private String secretKey;

    private KakaoReadyResponseDto kakaoReadyResponseDto;

    /**
     * 1. Ready (결제 준비)
     * 서버에서 카카오페이 서버로 결제 정보 전달
     * Secret Key를 헤더에 담아 파라미터 값들과 함께 POST로 요청
     * 결제 고유번호(TID)와 redirect URL을 응답받음
     * 
     * @return
     */
    public KakaoReadyResponseDto kakaoPayReady() {

        // 카카오페이 요청 양식
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", cid); // 가맹점 코드
        params.add("partner_order_id", "partner_order_id"); // 가맹점 주문번호
        params.add("partner_user_id", "partner_user_id"); // 가맹점 회원 ID
        params.add("item_name", "초코파이"); // 상품명
        params.add("quantity", "1"); // 상품 수량
        params.add("total_amount", "2200"); // 상품 총액
        params.add("tax_free_amount", "0"); // 상품 비과세 금액
        params.add("approval_url", "https://developers.kakao.com/success"); // 결제 성공 시 redirect url
        params.add("cancel_url", "https://developers.kakao.com/cancel"); // 결제 취소 시 redirect url
        params.add("fail_url", "https://developers.kakao.com/fail"); // 결제 실패 시 redirect url

        // 파라미터, 헤더 담기
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        // 결제정보를 담아 카카오페이 서버에 post 요청 보내기
        // 결제 고유번호(TID), URL 응답받음
        kakaoReadyResponseDto = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class
        );

        return kakaoReadyResponseDto;
    }

    // 카카오 요구 헤더값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String authorization = "KaKaoAK " + secretKey;

        httpHeaders.set("Authorization", authorization);
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        return httpHeaders;
    }


}
