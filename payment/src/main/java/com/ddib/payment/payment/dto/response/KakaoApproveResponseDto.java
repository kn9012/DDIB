package com.ddib.payment.payment.dto.response;

import com.ddib.payment.order.domain.Order;
import com.ddib.payment.payment.dto.Amount;
import com.ddib.payment.payment.dto.CardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KakaoApproveResponseDto {

    @Schema(description = "승인/취소/정기 결제 API 호출에 대한 고유번호 - 승인/취소가 구분된 결제번호 (각 API 호출 성공시 발급)")
    private String aid;

    @Schema(description = "결제 고유 번호 - 승인/취소가 동일한 결제번호")
    private String tid;

    @Schema(description = "가맹점 코드")
    private String cid;

    @Schema(description = "가맹점 주문번호 (최대 100자)")
    private String partner_order_id;

    @Schema(description = "가맹점 회원 ID (최대 100자)")
    private String partner_user_id;

    @Schema(description = "결제 수단 (CARD or MONEY)")
    private String payment_method_type;

    @Schema(description = "결제 금액 정보")
    private Amount amount;

    @Schema(description = "상품 원가")
    private int price;

    @Schema(description = "할인율")
    private double discount;

    @Schema(description = "결제 상세 정보 (결제 수단이 카드일 경우만)")
    private CardInfo card_info;

    @Schema(description = "상품 이름 (최대 100자)")
    private String item_name;

    @Schema(description = "상품 코드 (최대 100자)")
    private String item_code;

    @Schema(description = "상품 수량")
    private int quantity;

    @Schema(description = "결제 준비 요청 시각")
    private LocalDateTime created_at;

    @Schema(description = "결제 승인 시각")
    private LocalDateTime approved_at;

    @Schema(description = "결제 승인 요청에 대해 저장한 값, 요청 시 전달된 내용")
    private String payload;

    @Schema(description = "배송 받는 사람 이름")
    private String receiverName;

    @Schema(description = "배송 받는 사람 핸드폰 번호")
    private String receiverPhone;

    @Schema(description = "주문 상품을 배송할 도로명 주소")
    private String orderRoadAddress;

    @Schema(description = "주문 상품을 배송할 상세 주소")
    private String orderDetailAddress;

    @Schema(description = "주문 상품을 배송할 우편번호")
    private String orderZipcode;

    public void updateKakaoApproveResponseDto(Order order) {
        this.price = order.getProduct().getPrice();
        this.discount = order.getProduct().getDiscount();
        this.receiverName = order.getReceiverName();
        this.receiverPhone = order.getReceiverPhone();
        this.orderRoadAddress = order.getOrderRoadAddress();
        this.orderDetailAddress = order.getOrderDetailAddress();
        this.orderZipcode = order.getOrderZipcode();
    }

}
