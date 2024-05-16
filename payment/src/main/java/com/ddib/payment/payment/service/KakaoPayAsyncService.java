package com.ddib.payment.payment.service;

import com.ddib.payment.order.domain.Order;
import com.ddib.payment.order.domain.OrderStatus;
import com.ddib.payment.order.repository.OrderRepository;
import com.ddib.payment.payment.client.ProductClient;
import com.ddib.payment.payment.domain.Payment;
import com.ddib.payment.payment.domain.PaymentStatus;
import com.ddib.payment.payment.domain.Tid;
import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.request.ProductStockDecreaseRequestDto;
import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.dto.response.KakaoRefundResponseDto;
import com.ddib.payment.payment.repository.PaymentRepository;
import com.ddib.payment.payment.repository.TidRepository;
import com.ddib.payment.payment.util.KakaoProperties;
import com.ddib.payment.product.repository.ProductRepository;
import com.ddib.payment.product.service.ProductService;
import com.ddib.payment.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayAsyncService {

    @Value("${pay.kakao.cid}")
    private String cid;
    @Value("${pay.kakao.secret-key}")
    private String secretKey;

    private final ProductService productService;
    private final TidRepository tidRepository;
    private final ProductClient productClient;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RedissonClient redissonClient;
    private final KakaoProperties kakaoProperties;


    /**
     * 1. Ready (결제 준비)
     * 서버에서 카카오페이 서버로 결제 정보 전달
     * Secret Key를 헤더에 담아 파라미터 값들과 함께 POST로 요청
     * 결제 고유번호(TID)와 redirect URL을 응답받음
     */
    @Async
    public CompletableFuture<KakaoReadyResponseDto> kakaoPayReady(KakaoReadyRequestDto kakaoReadyRequestDto, String orderId) {

        log.info("===== Thread Name : " + Thread.currentThread().getName() + " =====");

        // 카카오페이 요청 양식
        Map<String, Object> params = new HashMap<>();
        params.put("cid", cid); // 가맹점 코드
        params.put("partner_order_id", orderId); // 가맹점 주문번호
        params.put("partner_user_id", "DDIB"); // 가맹점 회원 ID
        params.put("item_name", kakaoReadyRequestDto.getItemName()); // 상품명
        params.put("quantity", kakaoReadyRequestDto.getQuantity()); // 상품 수량
        params.put("total_amount", kakaoReadyRequestDto.getTotalAmount()); // 상품 총액
        params.put("tax_free_amount", kakaoReadyRequestDto.getTaxFreeAmount()); // 상품 비과세 금액
        params.put("approval_url", kakaoProperties.kakaoApprovalUrl + "?product_id=" + kakaoReadyRequestDto.getProductId() + "&quantity=" + kakaoReadyRequestDto.getQuantity() + "&order_id=" + orderId); // 결제 성공 시 redirect url (인증이 완료되면 approval_url로 redirect)
        params.put("cancel_url", kakaoProperties.kakaoCancelUrl + "?partner_order_id=" + orderId); // 결제 취소 시 redirect url
        params.put("fail_url", kakaoProperties.kakaoFailUrl + "?partner_order_id=" + orderId); // 결제 실패 시 redirect url

        // 파라미터, 헤더 담기
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, this.getHeaders());

        // 외부 API 호출 및 Server to Server 통신을 위해 사용
        RestTemplate restTemplate = new RestTemplate();

        // 결제정보를 담아 카카오페이 서버에 post 요청 보내기
        // 결제 고유번호(TID), URL 응답받음
        log.info("===== 카카오페이 서버로 post 요청 전송 =====");
        KakaoReadyResponseDto kakaoReadyResponseDto = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 서버에서 데이터 응답 완료 =====");

        // redis에 tid 저장
        Tid tid = Tid.builder()
                .orderId(orderId)
                .tid(kakaoReadyResponseDto.getTid())
                .build();
        tidRepository.save(tid);

        return CompletableFuture.completedFuture(kakaoReadyResponseDto);
    }


    @Async
    public void insertOrderData(KakaoReadyRequestDto kakaoReadyRequestDto, String orderId, int userId) {

        log.info("===== Thread Name : " + Thread.currentThread().getName() + " =====");

        // 주문 테이블에 Data Insert
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 데이터 insert 시작 =====");
        log.info("userId : " + userId);
        Order order = Order.builder()
                .orderId(orderId)
                .user(userRepository.findById(userId).get())
                .product(productRepository.findById(kakaoReadyRequestDto.getProductId()).get())
                .orderDate(new Timestamp(System.currentTimeMillis()))
                .productCount(kakaoReadyRequestDto.getQuantity())
                .totalPrice(kakaoReadyRequestDto.getTotalAmount())
                .receiverName(kakaoReadyRequestDto.getReceiverName())
                .receiverPhone(kakaoReadyRequestDto.getReceiverPhone())
                .orderRoadAddress(kakaoReadyRequestDto.getOrderRoadAddress())
                .orderDetailAddress(kakaoReadyRequestDto.getOrderDetailAddress())
                .orderZipcode(kakaoReadyRequestDto.getOrderZipcode())
                .status(OrderStatus.PAYMENT_COMPLETED)
                .build();
        orderRepository.save(order);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 데이터 insert 완료 =====");
    }


    @Transactional
//    @Async
    public KakaoApproveResponseDto afterPayApproveRequest(String pgToken, int productId, int quantity, String orderId) {
//    public CompletableFuture<KakaoApproveResponseDto> afterPayApproveRequest(String pgToken, int productId, int quantity, String orderId) {

        log.info(Thread.currentThread().getName() + " : afterPayApproveRequest 메서드 진입 (주문수량 : " + quantity + ")");

        // 상품 데이터에 Lock 걸어서 재고 조회

        // 특정 이름으로 Lock 정의
        final String lockName = "product" + productId;
        final RLock lock = redissonClient.getLock(lockName);
        final String worker = Thread.currentThread().getName();
        log.info(worker + " : lock 정의 완료");

        try {
            // 락 획득 시도 (20초 동안 시도하고 락을 획득할 경우 3초 후에 해제)
            boolean available = lock.tryLock(20, 10, TimeUnit.SECONDS);
            if(!available) {
                log.info("===== " + worker + " : Lock Get Fail =====");
                throw new RuntimeException("RuntimeException : Lock Get Fail");
            }

            // 락 획득 성공한 경우
            log.info("===== " + worker + " : Lock Get Success =====");
            int stock = productService.checkStock(productId); // 재고 조회
            log.info("===== " + worker + " : 현재 재고 수 = " + stock);

            if(stock > 0 && stock - quantity >= 0) {
                log.info("===== " + worker + " : 카카오페이 서버로 승인 요청 전송 =====");
                // 카카오페이 서버로 승인 요청 전송
                KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPayApprove(pgToken, orderId);
                // 배송 정보 등 필요한 응답 데이터 추가 업데이트
                Order order = orderRepository.findByOrderId(orderId);
                kakaoApproveResponseDto.updateKakaoApproveResponseDto(order);
                log.info("배송 정보 등 응답 데이터 업데이트 후 결제 승인 시각 : " + kakaoApproveResponseDto.getApproved_at());

                // 결제 데이터 insert (비동기)
                insertPaymentData(kakaoApproveResponseDto, order.getUser().getUserId());
                // Feign Client로 상품 서버 호출해서 재고 차감
                ProductStockDecreaseRequestDto productStockDecreaseRequestDto = ProductStockDecreaseRequestDto.builder()
                        .productId(productId)
                        .amount(quantity)
                        .build();
                productClient.decreaseStock(productStockDecreaseRequestDto);

                log.info("===== " + worker + " : stock update completed =====");

                return kakaoApproveResponseDto;
//                return CompletableFuture.supplyAsync(() -> kakaoApproveResponseDto);

            } else {
                deleteOrder(orderId);
                return null;
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } finally {
            lock.unlock();
            log.info("===== Unlock Completed =====");
        }
    }


    /**
     *
     * 2. Approve (결제 승인)
     * 사용자가 결제 수단을 선택하고 비밀번호를 입력해 결제 인증을 완료한 뒤, 최종적으로 결제 완료 처리를 하는 단계
     * 인증 완료시(테스트의 경우 비밀번호 입력 안하므로 결제하기 버튼 클릭시) 응답받은 pg_token과 tid로 최종 승인 요청함
     * 결제 승인 API를 호출하면 결제 준비 단계에서 시작된 결제건이 승인으로 완료 처리됨
     */
    public KakaoApproveResponseDto kakaoPayApprove(String pgToken, String orderId) {

        // 카카오페이 요청 양식
        Map<String, String> params = new HashMap<>();
        params.put("cid", cid);
        params.put("tid", tidRepository.findById(orderId).get().getTid());
        params.put("partner_order_id", orderId);
        params.put("partner_user_id", "DDIB");
        params.put("pg_token", pgToken);

        // 파라미터, 헤더 담기
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto kakaoApproveResponseDto = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        log.info("결제 승인 시각 : " + kakaoApproveResponseDto.getApproved_at());

        return kakaoApproveResponseDto;
    }


    /**
     * [ ConcurrencyTest.java ]
     * 동시성 제어 테스트를 위한 비동기 메서드
     * Redisson 적용 메서드
     */
    @Async
    public void buyProduct(int i, int productId, int quantity) {

        final String worker = Thread.currentThread().getName();
        log.info(worker + " : user" + i + " started to buy product");

        // 특정 이름으로 Lock 정의
        final String lockName = "product" + productId;
        final RLock lock = redissonClient.getLock(lockName);
        log.info(worker + " : user " + i + " defined lock");

        try {
            // 락 획득 시도 (20초 동안 시도하고 락을 획득할 경우 3초 후에 해제)
            boolean available = lock.tryLock(20, 3, TimeUnit.SECONDS);
            if(!available) {
                log.info("===== " + worker + " : Lock Get Fail =====");
                throw new RuntimeException("RuntimeException : Lock Get Fail");
            }

            // 락 획득 성공한 경우
            log.info("===== " + worker + " : Lock Get Success =====");
            int stock = productService.checkStock(productId); // 재고 조회
            log.info("===== " + worker + " : 현재 재고 수 = " + stock);

            if(stock > 0 && stock - quantity >= 0) {
                // 재고 차감
                productService.updateStock(productId, quantity);
                log.info("===== " + worker + " : stock update completed =====");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } finally {
            lock.unlock();
            log.info("===== Unlock Completed =====");
        }
    }


    /**
     * [ ConcurrencyTest.java ]
     * 동시성 제어 테스트를 위한 비동기 메서드
     * Redisson 적용 안한 메서드
     */
    @Async
    public void buyProductWithNoRedisson(int i, int productId, int quantity) {

        final String worker = Thread.currentThread().getName();
        log.info(worker + " : user" + i + " started to buy product");

        int stock = productService.checkStock(productId); // 재고 조회
        if(stock > 0 && stock - quantity >= 0) {
            // 재고 차감
            productService.updateStock(productId, quantity);
            log.info("===== " + worker + " : stock update completed =====");
        }
    }


    @Async
    public void insertPaymentData(KakaoApproveResponseDto kakaoApproveResponseDto, int userId) {

        // 결제 테이블에 Data Insert
        log.info("===== 결제 테이블에 Data Insert 시작 =====");
        log.info("insertPaymentData()에서의 결제 승인 시각 : " + kakaoApproveResponseDto.getApproved_at());

        Payment payment = Payment.builder()
                .tid(kakaoApproveResponseDto.getTid())
                .totalAmount(kakaoApproveResponseDto.getAmount().getTotal())
                .taxFree(kakaoApproveResponseDto.getAmount().getTax_free())
                .paymentMethodType(kakaoApproveResponseDto.getPayment_method_type())
                .paymentDate(kakaoApproveResponseDto.getApproved_at())
                .user(userRepository.findById(userId).get())
                .order(orderRepository.findByOrderId(kakaoApproveResponseDto.getPartner_order_id()))
                .status(PaymentStatus.PAYMENT_COMPLETED)
                .build();
        paymentRepository.save(payment);
        log.info("===== 결제 테이블에 Data Insert 완료 =====");
    }


    /**
     * 1. 결제 진행 중 취소일 때
     * 2. 결제 실패일 때
     * 3. 결제 승인 중 재고가 없어서 주문 데이터 삭제할 때
     */
    @Async
    @Transactional
    public void deleteOrder(String orderId) {
        orderRepository.deleteByOrderId(orderId);
    }


    /**
     * 환불 (결제 취소)
     *
     * 결제 고유번호(tid)에 해당하는 결제건에 대해 지정한 금액만큼 결제 취소를 요청
     * 취소 요청시 비과세(tax_free_amount)와 부가세(vat_amount)를 맞게 요청해야 함
     */
    // 동시성 제어 처리 안했을 때
//    @Async
//    @Transactional
//    public void refund(String orderId) {
//
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 환불 시작 =====");
//
//        Payment payment = paymentRepository.findByOrderId(orderId);
//        Order order = orderRepository.findByOrderId(orderId);
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 환불 해당 주문건 조회 완료 =====");
//
//        // 카카오 요청 양식
//        Map<String, Object> params = new HashMap<>();
//        params.put("cid", cid); // 가맹점 코드
//        params.put("tid", payment.getTid()); // 결제 고유번호
//        params.put("cancel_amount", payment.getTotalAmount()); // 취소 금액
//        params.put("cancel_tax_free_amount", payment.getTaxFree()); // 취소 비과세 금액
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, this.getHeaders());
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        KakaoRefundResponseDto kakaoRefundResponseDto = restTemplate.postForObject(
//                "https://open-api.kakaopay.com/online/v1/payment/cancel",
//                requestEntity,
//                KakaoRefundResponseDto.class);
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 서버에서 데이터 응답 완료 =====");
//
//        // 주문 및 결제 상태 바꾸기
//        payment.updateStatus(PaymentStatus.REFUND_COMPLETED);
//        order.updateStatus(OrderStatus.CANCELED);
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 및 결제 상태 변경 완료 =====");
//
//        // 환불 시간 기록
//        payment.updateRefundedAt(kakaoRefundResponseDto.getCanceled_at());
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 환불 시간 기록 완료 =====");
//    }


    // 동시성 제어 처리했을 때
    @Async
    @Transactional
    public void refund(String orderId) {

        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 환불 시작 =====");

        Payment payment = paymentRepository.findByOrderId(orderId);
        Order order = orderRepository.findByOrderId(orderId);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 환불 해당 주문건 조회 완료 =====");

        // 특정 이름으로 Lock 정의
        int productId = order.getProduct().getProductId();
        final String lockName = "product" + productId;
        final RLock lock = redissonClient.getLock(lockName);
        final String worker = Thread.currentThread().getName();
        log.info(worker + " : lock 정의 완료");

        try {
            // 락 획득 시도 (20초 동안 시도하고 락을 획득할 경우 3초 후에 해제)
            boolean available = lock.tryLock(20, 3, TimeUnit.SECONDS);
            if(!available) {
                log.info("===== " + worker + " : Lock Get Fail =====");
                throw new RuntimeException("RuntimeException : Lock Get Fail");
            }

            log.info("===== " + worker + " : Lock Get Success =====");
            int stock = productService.checkStock(productId); // 재고 조회
            int totalStock = productRepository.findById(productId).get().getTotalStock(); // 상품의 총 재고 수

            if(stock + order.getProductCount() <= totalStock) {

                // 카카오 요청 양식
                Map<String, Object> params = new HashMap<>();
                params.put("cid", cid); // 가맹점 코드
                params.put("tid", payment.getTid()); // 결제 고유번호
                params.put("cancel_amount", payment.getTotalAmount()); // 취소 금액
                params.put("cancel_tax_free_amount", payment.getTaxFree()); // 취소 비과세 금액

                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, this.getHeaders());
                log.info("===== Thread Name : " + Thread.currentThread().getName() + " httpEntity 생성 완료 =====");

                RestTemplate restTemplate = new RestTemplate();

                KakaoRefundResponseDto kakaoRefundResponseDto = restTemplate.postForObject(
                        "https://open-api.kakaopay.com/online/v1/payment/cancel",
                        requestEntity,
                        KakaoRefundResponseDto.class);
                log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 서버에서 데이터 응답 완료 =====");

                // 주문 및 결제 상태 바꾸기
                payment.updateStatus(PaymentStatus.REFUND_COMPLETED);
                order.updateStatus(OrderStatus.CANCELED);
                log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 및 결제 상태 변경 완료 =====");

                // 환불 시간 기록
                payment.updateRefundedAt(kakaoRefundResponseDto.getCanceled_at());
                log.info("===== Thread Name : " + Thread.currentThread().getName() + " 환불 시간 기록 완료 =====");

                productService.updateStockByRefund(productId, order.getProductCount()); // 재고 증가

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } finally {
            lock.unlock();
            log.info("===== Unlock Completed =====");
        }
    }


    /**
     * http 헤더에 카카오 요구 헤더값인 secret key 담기
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String authorization = "SECRET_KEY " + secretKey;

        httpHeaders.set("Authorization", authorization);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }

}
