package com.ddib.payment.payment.controller;

import com.ddib.payment.order.util.OrderIdGenerator;
import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.service.KakaoPayAsyncService;
import com.ddib.payment.payment.service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class PaymentControllerTest {

    @Autowired
    private KakaoPayService kakaoPayService;
    @Autowired
    private KakaoPayAsyncService kakaoPayAsyncService;

    @Autowired
//    @Qualifier("taskExecutor")
//    private Executor executor;

    /**
     * 1. 동기
     */
//    @Test
//    public void callReadyBySync() {
//
//        int userId = 9;
//
//        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
//                .productId(1)
//                .itemName("나이키 에어포스")
//                .quantity(1)
//                .totalAmount(80000)
//                .taxFreeAmount(0)
//                .receiverName("유세진")
//                .receiverPhone("01066041442")
//                .orderRoadAddress("서울특별시 관악구 은천로")
//                .orderDetailAddress("102동 705호")
//                .orderZipcode("08715")
//                .build();
//
//        long startTime = System.currentTimeMillis();
//        for(int i=0; i<500; i++) {
//            kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, userId);
//        }
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        log.info("===== Sync Execution Time : " + elapsedTime + " =====");
//    }

    /**
     * 2. 비동기 (기본 ThreadPoolTaskExecutor)
     */
    @Test
    public void callReadyByAsync() {

        int userId = 9;

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
        for (int i = 0; i < 100; i++) {
            String orderId = OrderIdGenerator.generateOrderId();
            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);
            CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto).join();
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("===== Async Execution Time (Default ThreadPoolTaskExecutor) : " + elapsedTime + " =====");
    }

    /**
     * 3. 비동기 (커스텀 ThreadPoolTaskExecutor)
     */
//    @Test
//    public void callReadyByAsyncAndThreadPool() {
//
//        int userId = 9;
//
//        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
//                .productId(1)
//                .itemName("나이키 에어포스")
//                .quantity(1)
//                .totalAmount(80000)
//                .taxFreeAmount(0)
//                .receiverName("유세진")
//                .receiverPhone("01066041442")
//                .orderRoadAddress("서울특별시 관악구 은천로")
//                .orderDetailAddress("102동 705호")
//                .orderZipcode("08715")
//                .build();
//
//        long startTime = System.currentTimeMillis();
//        for(int i=0; i<100; i++) {
//            String orderId = OrderIdGenerator.generateOrderId();
//            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
//            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);
//            CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto, executor).join();
//        }
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        log.info("===== Async Execution Time (Custom ThreadPoolTaskExecutor) : " + elapsedTime + " =====");
//    }

}
