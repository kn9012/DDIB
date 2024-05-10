//package com.ddib.payment.payment.controller;
//
//import com.ddib.payment.payment.service.KakaoPayAsyncService;
//import com.ddib.payment.product.service.ProductService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.annotation.EnableAsync;
//
//@Slf4j
//@EnableAsync
//@SpringBootTest
//public class ConcurrencyTest {
//
//    @Autowired
//    private RedissonClient redissonClient;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private KakaoPayAsyncService kakaoPayAsyncService;
//
//    @Test
//    public void concurrencyTest() {
//        int productId = 1;
//        int quantity = 1;
//
//        // 동시 사용자 100명으로 가정
//        for(int i=1; i<=100; i++) {
//
//            // 동시성 제어 처리 X
////            kakaoPayAsyncService.buyProductWithNoRedisson(i, productId, quantity);
//
//            // 동시성 제어 처리 O
//            kakaoPayAsyncService.buyProduct(i, productId, quantity);
//
//        }
//    }
//
//}
