package com.ddib.payment.payment.controller;

import com.ddib.payment.order.util.OrderIdGenerator;
import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayAsyncService;
import com.ddib.payment.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    private KakaoPayService kakaoPayService;
    private KakaoPayAsyncService kakaoPayAsyncService;

    @Qualifier("taskExecutor")
    private final Executor executor;

    @Test
//    @WithAuth
    public void callReadyBySync(Principal principal) {

        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
                .productId(1)
                .itemName("나이키 에어포스")
                .quantity(1)
                .totalAmount(80000)
                .taxFreeAmount(0)
                .receiverName("유세진")
                .receiverPhone("01066041442")
                .orderRoadAddress("서울특별시 관악구 은천로")
                .orderDetailAddress("102동 705호")
                .orderZipcode("08715")
                .build();

        long startTime = System.currentTimeMillis();
        for(int i=0; i<500; i++) {
            kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, principal);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("===== Sync Execution Time : " + elapsedTime + " =====");
    }

    @Test
    public void callReadyByAsync(Principal principal) {

        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
                .productId(1)
                .itemName("나이키 에어포스")
                .quantity(1)
                .totalAmount(80000)
                .taxFreeAmount(0)
                .receiverName("유세진")
                .receiverPhone("01066041442")
                .orderRoadAddress("서울특별시 관악구 은천로")
                .orderDetailAddress("102동 705호")
                .orderZipcode("08715")
                .build();

        String orderId = OrderIdGenerator.generateOrderId();

        long startTime = System.currentTimeMillis();
        for(int i=0; i<500; i++) {
            kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, principal);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("===== Async Execution Time : " + elapsedTime + " =====");
    }

    @Test
    public void callReadyByAsyncAndThreadPool(Principal principal) {

        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
                .productId(1)
                .itemName("나이키 에어포스")
                .quantity(1)
                .totalAmount(80000)
                .taxFreeAmount(0)
                .receiverName("유세진")
                .receiverPhone("01066041442")
                .orderRoadAddress("서울특별시 관악구 은천로")
                .orderDetailAddress("102동 705호")
                .orderZipcode("08715")
                .build();

        String orderId = OrderIdGenerator.generateOrderId();

        long startTime = System.currentTimeMillis();
        for(int i=0; i<500; i++) {
            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, principal);
            CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto, executor).join();
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("===== Async Execution Time : " + elapsedTime + " =====");
    }

}
