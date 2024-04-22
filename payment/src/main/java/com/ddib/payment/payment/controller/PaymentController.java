package com.ddib.payment.payment.controller;

import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto) {
        KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady(kakaoReadyRequestDto);
        return new ResponseEntity<>(kakaoReadyResponseDto, HttpStatus.OK);
    }

    /**
     * 결제 성공 -> 승인 요청
     * 결제 성공 시 pgToken을 가지고 승인 요청을 보냄
     */
    @Operation(summary = "카카오페이 결제 성공시 승인 요청하는 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponseDto> afterPayApproveRequest(@RequestParam("pg_token") String pgToken) {
        KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayService.kakaoPayApprove(pgToken);
        return new ResponseEntity<>(kakaoApproveResponseDto, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @Operation(summary = "카카오페이 결제 진행 중 취소 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/cancel")
    public ResponseEntity<Void> cancel() {
//        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 임시
    }

    /**
     * 결제 실패
     */
    @Operation(summary = "결제 실패 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/fail")
    public ResponseEntity<Void> fail() {
//        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 임시
    }

}
