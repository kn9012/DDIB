package com.ddib.payment.payment.controller;

import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제 요청
     */
    @Operation(summary = "카카오페이 결제 요청 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/ready")
    public ResponseEntity<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, Principal principal) {
        KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, principal);
        return new ResponseEntity<>(kakaoReadyResponseDto, HttpStatus.OK);
    }

    /**
     * 결제 성공 -> 승인 요청
     * 결제 성공 시 pgToken을 가지고 승인 요청을 보냄
     */
    @Operation(summary = "카카오페이 결제 성공시 승인 요청하는 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponseDto> afterPayApproveRequest(@RequestParam("pg_token") String pgToken, Principal principal) {
        log.info("===== 결제 승인 API 시작 =====");
        KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayService.kakaoPayApprove(pgToken, principal);
        return new ResponseEntity<>(kakaoApproveResponseDto, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @Operation(summary = "카카오페이 결제 진행 중 취소 API")
    @ApiResponse(responseCode = "200", description = "성공(결제 취소 시 결제하기 페이지로 다시 redirect 해주세요.)")
    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        log.info("===== 결제 진행 중 취소 =====");
        return new ResponseEntity<>("사용자가 결제 진행 중 결제를 취소했습니다.", HttpStatus.OK);
    }

    /**
     * 결제 실패
     * <결제 실패 케이스 3가지>
     * 1. 비밀번호 틀림
     * 2. 비밀번호 2차인증 실패
     * 3. 결제 준비 성공 후 15분 경과 시
     */
    @Operation(summary = "결제 실패 API")
    @ApiResponse(responseCode = "200", description = "성공(결제 실패 시 결제하기 페이지로 다시 redirect 해주세요.)")
    @GetMapping("/fail")
    public ResponseEntity<String> fail() {
        log.info("===== 결제 실패 =====");
        return new ResponseEntity<>("사용자가 결제에 실패했습니다.", HttpStatus.OK);
    }

    /**
     * 환불
     */


}
