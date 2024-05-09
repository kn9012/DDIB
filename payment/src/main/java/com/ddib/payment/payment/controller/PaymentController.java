package com.ddib.payment.payment.controller;

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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final KakaoPayAsyncService kakaoPayAsyncService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderIdGenerator orderIdGenerator;
    private final RedissonClient redissonClient;
    private final RedissonClient redisson;

//    @Qualifier("taskExecutor")
//    private final Executor executor;

    /**
     * 결제 요청
     */
    @Operation(summary = "카카오페이 결제 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "재고 없음")
    })
    @PostMapping("/ready/{userId}")
    // 1. 동기 방식
//    public ResponseEntity<?> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, @PathVariable int userId) {
//    public ResponseEntity<?> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto) {
//        // 재고 조회
//        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());
//
//        if(stock > 0) {
//            KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, userId);
//            KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayService.kakaoPayReady(kakaoReadyRequestDto);
//            return new ResponseEntity<>(kakaoReadyResponseDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    // 2. 비동기 방식 (기본 ThreadPoolTaskExecutor)
    public CompletableFuture<?> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, @PathVariable int userId) {
        log.info("============ 결제 준비 요청 API 시작 ===============");
        // 재고 조회
        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());

        if(stock > 0) {
            String orderId = OrderIdGenerator.generateOrderId();
            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);

            log.info("============= 끝 ===================");

//            return CompletableFuture.supplyAsync(() -> {
//                CompletableFuture<KakaoReadyResponseDto> dto = kakaoReadyResponseDto;
//                log.info("============= return ===================");
//                log.info(Thread.currentThread().getName());
//                return dto;});

            return CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto).join();
        } else {
            return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }

    // 3. 비동기 방식 (커스텀 ThreadPoolTaskExecutor)
//    public CompletableFuture<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto, int userId) {
//    public CompletableFuture<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto) {
//        log.info("================================");
//        // 재고 조회
//        int stock = productService.checkStock(kakaoReadyRequestDto.getProductId());
//
//        if(stock > 0) {
//            String orderId = OrderIdGenerator.generateOrderId();
//            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
////            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);
//            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId);
//
//            log.info("============= 끝 ===================");
//
////            return CompletableFuture.supplyAsync(() -> {
////                CompletableFuture<KakaoReadyResponseDto> dto = kakaoReadyResponseDto;
////                log.info("============= return ===================");
////                log.info(Thread.currentThread().getName());
////                return dto;});
//
//            return CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto, executor).join();
//        } else {
//            return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//        }
//    }


    /**
     * 결제 성공 -> 승인 요청
     * 결제 성공 시 pgToken을 가지고 승인 요청을 보냄
     */
    @Operation(summary = "카카오페이 결제 성공시 승인 요청하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "재고없음 / 주문 수량이 현재 재고보다 많음"),
    })
    @GetMapping("/success")
    @Async
    public CompletableFuture<?> afterPayApproveRequest(@RequestParam("pg_token") String pgToken, @RequestParam("product_id") int productId, @RequestParam("quantity") int quantity, @RequestParam("order_id") String orderId) {

        log.info("===== 결제 승인 API 시작 : 주문 수량은 " + quantity +  "개 =====");

        CompletableFuture<KakaoApproveResponseDto> kakaoApproveResponseDto = kakaoPayAsyncService.afterPayApproveRequest(pgToken, productId, quantity, orderId);

        log.info(Thread.currentThread().getName() + "카카오 승인 후 재고 차감까지 완료한 후 현재 contoller단");

        RedirectView redirectView = new RedirectView();
        if(kakaoApproveResponseDto != null) {
            log.info(Thread.currentThread().getName() + "응답 완료 직전");
//            return CompletableFuture.supplyAsync(() -> kakaoApproveResponseDto).join();
            redirectView.setUrl("https://k10c102.p.ssafy.io/order/complete/" + orderId);
            return CompletableFuture.supplyAsync(() -> redirectView);
        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            redirectView.setUrl("https://k10c102.p.ssafy.io/order/fail");
            return CompletableFuture.supplyAsync(() -> redirectView);
        }
    }


    /**
     * 결제 진행 중 취소
     */
    @Operation(summary = "카카오페이 결제 진행 중 취소 API")
    @ApiResponse(responseCode = "200", description = "성공(결제 취소 시 결제하기 페이지로 다시 redirect 해주세요.)")
    @GetMapping("/cancel")
    public ResponseEntity<Object> cancel(@RequestParam("partner_order_id") String orderId, HttpServletResponse response) throws IOException {
        log.info("===== 결제 진행 중 취소 =====");

        // 1. 동기 방식
//        kakaoPayService.cancel(orderId);

        // 2. 비동기 방식 (기본 ThreadPoolTaskExecutor)
        kakaoPayAsyncService.deleteOrder(orderId);

//        try {
//            URI redirectUri = new URI("https://k10c102.p.ssafy.io/order/cancel");
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(redirectUri);
//            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }

        response.sendRedirect("https://k10c102.p.ssafy.io/order/cancel");
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<Object> fail(@RequestParam("partner_order_id") String orderId) {
        log.info("===== 결제 실패 =====");

        // 1. 동기 방식
//        kakaoPayService.fail(orderId);

        // 2. 비동기 방식 (스레드 풀x)
        kakaoPayAsyncService.deleteOrder(orderId);

        try {
            URI redirectUri = new URI("https://k10c102.p.ssafy.io/order/fail");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 환불 (결제 취소)
     */
    @Operation(summary = "카카오페이 환불 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @PutMapping("/refund/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable String orderId) {

        // 1. 동기 방식
//        kakaoPayService.refund(orderId);

        // 2. 비동기 방식 (스레드 풀x)
        CompletableFuture.runAsync(() -> kakaoPayAsyncService.refund(orderId));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
