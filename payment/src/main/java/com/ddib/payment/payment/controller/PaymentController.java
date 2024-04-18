package com.ddib.payment.payment.controller;

import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    // 결제 요청
    @Operation(summary = "카카오페이 결제 요청 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/ready")
    public ResponseEntity<KakaoReadyResponseDto> readyToKakaoPay() {
        KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady();
        return new ResponseEntity<>(kakaoReadyResponseDto, HttpStatus.OK);
    }

    // 결제 진행 중 취소
    @Operation(summary = "카카오페이 결제 진행 중 취소 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/cancel")
    public ResponseEntity<Void> cancel() {
//        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 임시
    }

    // 결제 실패
    @Operation(summary = "결제 실패 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/fail")
    public ResponseEntity<Void> fail() {
//        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 임시
    }

}
