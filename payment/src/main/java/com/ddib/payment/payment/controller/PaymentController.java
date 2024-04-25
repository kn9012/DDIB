package com.ddib.payment.payment.controller;

import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayService;
import com.ddib.payment.product.repository.ProductRepository;
import com.ddib.payment.product.service.ProductService;
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
    private final ProductService productService;

    /**
     * 결제 요청
     */
    @Operation(summary = "카카오페이 결제 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "재고 없음")
    })
    @PostMapping("/ready")
    public ResponseEntity<?> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, Principal principal) {
        // 재고 조회
        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());

        if(stock > 0) {
            KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, principal);
            return new ResponseEntity<>(kakaoReadyResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 결제 성공 -> 승인 요청
     * 결제 성공 시 pgToken을 가지고 승인 요청을 보냄
     */
    @Operation(summary = "카카오페이 결제 성공시 승인 요청하는 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponseDto> afterPayApproveRequest(@RequestParam("pg_token") String pgToken, @RequestParam("product_id") int productId, Principal principal) {
        log.info("===== 결제 승인 API 시작 =====");
        KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayService.kakaoPayApprove(pgToken, principal);

        // 재고 차감
        productService.updateStock(productId);

        return new ResponseEntity<>(kakaoApproveResponseDto, HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @Operation(summary = "카카오페이 결제 진행 중 취소 API")
    @ApiResponse(responseCode = "200", description = "성공(결제 취소 시 결제하기 페이지로 다시 redirect 해주세요.)")
    @GetMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestParam("partner_order_id") String orderId) {
        log.info("===== 결제 진행 중 취소 =====");
        kakaoPayService.cancel(orderId);
        return new ResponseEntity<>("사용자가 결제 진행 중 결제를 취소했습니다.", HttpStatus.OK);
    }

    /**
     * 결제 실패
     * <결제 실패 케이스 3가지>
     * 1. 비밀번호 틀림
     * 2. 비밀번호 2차인증 실패
     * 3. 결제 준비 성공 후 15분 경과 시
     */
    @Operation(summary = "카카오페이 결제 실패 API")
    @ApiResponse(responseCode = "200", description = "성공(결제 실패 시 결제하기 페이지로 다시 redirect 해주세요.)")
    @GetMapping("/fail")
    public ResponseEntity<String> fail(@RequestParam("partner_order_id") String orderId) {
        log.info("===== 결제 실패 =====");
        kakaoPayService.fail(orderId);
        return new ResponseEntity<>("사용자가 결제에 실패했습니다.", HttpStatus.OK);
    }

    /**
     * 환불 (결제 취소)
     */
    @Operation(summary = "카카오페이 환불 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/refund/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable String orderId) {
        kakaoPayService.refund(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
