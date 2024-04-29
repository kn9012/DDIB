package com.ddib.payment.payment.controller;

import com.ddib.payment.PaymentApplication;
import com.ddib.payment.order.service.OrderService;
import com.ddib.payment.order.util.OrderIdGenerator;
import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayAsyncService;
import com.ddib.payment.payment.service.KakaoPayService;
import com.ddib.payment.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final KakaoPayAsyncService kakaoPayAsyncService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderIdGenerator orderIdGenerator;

    @Qualifier("taskExecutor")
    private final Executor executor;

    /**
     * 결제 요청
     */
    @Operation(summary = "카카오페이 결제 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "재고 없음")
    })
    @PostMapping("/ready")
    // 1. 동기 방식
//    public ResponseEntity<?> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, Principal principal) {
//        // 재고 조회
//        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());
//
//        if(stock > 0) {
//            KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, principal);
//            return new ResponseEntity<>(kakaoReadyResponseDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    // 2. 비동기 방식 (스레드 풀x)
//    public CompletableFuture<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, Principal principal) {
//        log.info("================================");
//        // 재고 조회
//        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());
//
//        if(stock > 0) {
//            String orderId = OrderIdGenerator.generateOrderId();
//            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
//            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, principal);
//
//            log.info("============= 끝 ===================");
//
////            return CompletableFuture.supplyAsync(() -> {
////                CompletableFuture<KakaoReadyResponseDto> dto = kakaoReadyResponseDto;
////                log.info("============= return ===================");
////                log.info(Thread.currentThread().getName());
////                return dto;});
//
//            return CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto).join();
//        } else {
//            return null;
//        }
//    }

    // 3. 비동기 방식 (스레드 풀)
    public CompletableFuture<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, Principal principal) {
        log.info("================================");
        // 재고 조회
        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());

        if(stock > 0) {
            String orderId = OrderIdGenerator.generateOrderId();
            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, principal);

            log.info("============= 끝 ===================");

//            return CompletableFuture.supplyAsync(() -> {
//                CompletableFuture<KakaoReadyResponseDto> dto = kakaoReadyResponseDto;
//                log.info("============= return ===================");
//                log.info(Thread.currentThread().getName());
//                return dto;});

            return CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto, executor).join();
        } else {
            return null;
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

        // 1. 동기 방식
//        KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayService.kakaoPayApprove(pgToken, principal);

        // 2. 비동기 방식 (스레드 풀x)
        KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayAsyncService.kakaoPayApprove(pgToken, principal);

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

        // 1. 동기 방식
//        kakaoPayService.cancel(orderId);

        // 2. 비동기 방식 (스레드 풀x)
        kakaoPayAsyncService.cancel(orderId);

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

        // 1. 동기 방식
//        kakaoPayService.fail(orderId);

        // 2. 비동기 방식 (스레드 풀x)
        kakaoPayAsyncService.fail(orderId);

        return new ResponseEntity<>("사용자가 결제에 실패했습니다.", HttpStatus.OK);
    }

    /**
     * 환불 (결제 취소)
     */
    @Operation(summary = "카카오페이 환불 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/refund/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable String orderId) {

        // 1. 동기 방식
//        kakaoPayService.refund(orderId);

        // 2. 비동기 방식 (스레드 풀x)
        CompletableFuture.runAsync(() -> kakaoPayAsyncService.refund(orderId));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
