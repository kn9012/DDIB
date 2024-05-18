//package com.ddib.payment.payment.controller;
//
//import com.ddib.payment.order.util.OrderIdGenerator;
//import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
//import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
//import com.ddib.payment.payment.service.KakaoPayAsyncService;
//import com.ddib.payment.payment.service.KakaoPayService;
//import io.reactivex.rxjava3.core.Completable;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@Slf4j
//public class PaymentControllerTest {
//
//    @Autowired
//    private KakaoPayService kakaoPayService;
//    @Autowired
//    private KakaoPayAsyncService kakaoPayAsyncService;
//
////    @Autowired
////    @Qualifier("taskExecutor")
////    private Executor executor;
//
//    /**
//     * 1. 동기
//     */
////    @Test
////    public void callReadyBySync() {
////
////        int userId = 1;
////
////        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
////                .productId(1)
////                .itemName("나이키 에어포스")
////                .quantity(1)
////                .totalAmount(80000)
////                .taxFreeAmount(0)
////                .receiverName("유세진")
////                .receiverPhone("01066041442")
////                .orderRoadAddress("서울특별시 관악구 은천로")
////                .orderDetailAddress("102동 705호")
////                .orderZipcode("08715")
////                .build();
////
////        long startTime = System.currentTimeMillis();
////        for(int i=0; i<1000; i++) {
////            String orderId = OrderIdGenerator.generateOrderId();
////            kakaoPayService.kakaoPayReady(kakaoReadyRequestDto, orderId, userId);
////        }
////        long endTime = System.currentTimeMillis();
////        long elapsedTime = endTime - startTime;
////        log.info("===== Sync Execution Time : " + elapsedTime + " =====");
////    }
//
//    /**
//     * 2. 비동기 (기본 ThreadPoolTaskExecutor)
//     * 요청이 끝나면 바로 응답 시간 계산
//     */
////    @Test
////    public void callReadyByAsync() throws ExecutionException, InterruptedException {
////
////        int userId = 1;
////
////        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
////                .productId(1)
////                .itemName("나이키 에어포스")
////                .quantity(1)
////                .totalAmount(80000)
////                .taxFreeAmount(0)
////                .receiverName("유세진")
////                .receiverPhone("01066041442")
////                .orderRoadAddress("서울특별시 관악구 은천로")
////                .orderDetailAddress("102동 705호")
////                .orderZipcode("08715")
////                .build();
////
////        long startTime = System.currentTimeMillis();
////        for (int i = 0; i < 500; i++) {
////            String orderId = OrderIdGenerator.generateOrderId();
////            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
////            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);
////            CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto).join();
//////            kakaoReadyResponseDto.join();
////
//////            log.info("kakaoReadyResponseDto:{}", kakaoReadyResponseDto.get().getTid() + " / " + kakaoReadyResponseDto.get().getCreated_at());
//////            System.out.println("kakaoReadyResponseDto : " + kakaoReadyResponseDto.get().getTid() + " / " + kakaoReadyResponseDto.get().getCreated_at());
//////            CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto).join();
////        }
////        long endTime = System.currentTimeMillis();
////        long elapsedTime = endTime - startTime;
////        log.info("===== Async Execution Time (Default ThreadPoolTaskExecutor) : " + elapsedTime + " =====");
////    }
//
////    @Test
////    public void callReadyByAsync() throws ExecutionException, InterruptedException {
////
////        int userId = 1;
////
////        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
////                .productId(1)
////                .itemName("나이키 에어포스")
////                .quantity(1)
////                .totalAmount(80000)
////                .taxFreeAmount(0)
////                .receiverName("유세진")
////                .receiverPhone("01066041442")
////                .orderRoadAddress("서울특별시 관악구 은천로")
////                .orderDetailAddress("102동 705호")
////                .orderZipcode("08715")
////                .build();
////
////        List<CompletableFuture<KakaoReadyResponseDto>> futures = new ArrayList<>();
////
////        long startTime = System.currentTimeMillis();
////        for (int i = 0; i < 1000; i++) {
////            String orderId = OrderIdGenerator.generateOrderId();
////            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
////            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);
////            futures.add(kakaoReadyResponseDto);
////        }
////
////        // 모든 비동기 작업이 완료될 때까지 기다린다
////        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
////        allOf.join();
////
////        long endTime = System.currentTimeMillis();
////        long elapsedTime = endTime - startTime;
////        log.info("===== Async Execution Time (Default ThreadPoolTaskExecutor) : " + elapsedTime + " =====");
////    }
//
//    @Test
//    public void callReadyByAsync() throws ExecutionException, InterruptedException {
//
//        int userId = 1;
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
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            String orderId = OrderIdGenerator.generateOrderId();
//            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
//            CompletableFuture<Void> insertOrderFuture = kakaoPayAsyncService.insertOrderDataCF(kakaoReadyRequestDto, orderId, userId);
//            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(kakaoReadyResponseDto, insertOrderFuture)
//                    .thenApplyAsync(ignored -> {
//                        try {
//                            // Ensure that the kakaoReadyFuture is completed
//                            kakaoReadyResponseDto.get();
//                        } catch (InterruptedException | ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    });
//            futures.add(combinedFuture);
//        }
//
//        // 모든 비동기 작업이 완료될 때까지 기다린다
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
//        allOf.join();
//
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        log.info("===== Async Execution Time (Default ThreadPoolTaskExecutor) : " + elapsedTime + " =====");
//    }
//
//    /**
//     * 3. 비동기 (커스텀 ThreadPoolTaskExecutor)
//     */
////    @Test
////    public void callReadyByAsyncAndThreadPool() {
////
////        int userId = 1;
////
////        KakaoReadyRequestDto kakaoReadyRequestDto = KakaoReadyRequestDto.builder()
////                .productId(1)
////                .itemName("나이키 에어포스")
////                .quantity(1)
////                .totalAmount(80000)
////                .taxFreeAmount(0)
////                .receiverName("유세진")
////                .receiverPhone("01066041442")
////                .orderRoadAddress("서울특별시 관악구 은천로")
////                .orderDetailAddress("102동 705호")
////                .orderZipcode("08715")
////                .build();
////
////        long startTime = System.currentTimeMillis();
////        for(int i=0; i<100; i++) {
////            String orderId = OrderIdGenerator.generateOrderId();
////            CompletableFuture<KakaoReadyResponseDto> kakaoReadyResponseDto = kakaoPayAsyncService.kakaoPayReady(kakaoReadyRequestDto, orderId);
////            kakaoPayAsyncService.insertOrderData(kakaoReadyRequestDto, orderId, userId);
////            CompletableFuture.supplyAsync(() -> kakaoReadyResponseDto, executor).join();
////        }
////        long endTime = System.currentTimeMillis();
////        long elapsedTime = endTime - startTime;
////        log.info("===== Async Execution Time (Custom ThreadPoolTaskExecutor) : " + elapsedTime + " =====");
////    }
//
//}
