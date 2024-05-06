package com.ddib.payment.payment.controller;


import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.service.KakaoPayAsyncService;
import com.ddib.payment.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.TimeUnit;

@Slf4j
@EnableAsync
@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ProductService productService;
    @Autowired
    private KakaoPayAsyncService kakaoPayAsyncService;

    @Test
    public void concurrencyTest() {
        int productId = 1;
        int quantity = 1;

        // 동시 사용자 2명으로 가정
        for(int i=1; i<=100; i++) {
            kakaoPayAsyncService.buyProduct(i, productId, quantity);
//            kakaoPayAsyncService.buyProductWithNoRedisson(i, productId, quantity);
//            buyProduct(i, productId, quantity);
        }
    }

//    @Test
//    @Async
//    public void buyProduct(int i, int productId, int quantity) {
//        final String worker = Thread.currentThread().getName();
//        log.info(worker + " : " + i + "번 사용자 상품 구매 시작");
//
//        // 특정 이름으로 Lock 정의
//        final String lockName = "product" + productId;
//        final RLock lock = redissonClient.getLock(lockName);
//        log.info(worker + " : " + i + "번 사용자 lock 정의 완료");
//
//        try {
//            // 락 획득 시도 (20초 동안 시도하고 락을 획득할 경우 3초 후에 해제)
//            boolean available = lock.tryLock(20, 3, TimeUnit.SECONDS);
//            if(!available) {
//                log.info("===== " + worker + " : Lock 획득 실패 =====");
//                throw new RuntimeException("Lock을 획득하지 못했습니다.");
//            }
//
//            // 락 획득 성공한 경우
//            log.info("===== " + worker + " : Lock 획득 성공 =====");
//            int stock = productService.checkStock(productId); // 재고 조회
//            if(stock > 0 && stock - quantity >= 0) {
//                // 카카오페이 서버로 승인 요청 전송
////                KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayAsyncService.kakaoPayApprove(pgToken);
//
//                // 결제 데이터 insert (비동기)
////                kakaoPayAsyncService.insertPaymentData(kakaoApproveResponseDto, principal);
//                // 재고 차감
//                productService.updateStock(productId, quantity);
//                log.info("===== " + worker + " : 재고 차감 완료 =====");
//
////                return new ResponseEntity<>(kakaoApproveResponseDto, HttpStatus.OK);
//
//            }
////            else {
////                kakaoPayAsyncService.deleteOrder(orderId);
////                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////            }
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//
//        } finally {
//            lock.unlock();
//            log.info("===== Lock 해제 완료 =====");
//        }
//    }

}
