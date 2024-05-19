package com.ddib.payment.payment.dto.response;

import com.ddib.payment.payment.dto.Amount;
import com.ddib.payment.payment.dto.ApprovedCancelAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KakaoRefundResponseDto {

    @Schema(description = "요청 고유 번호")
    private String aid;

    @Schema(description = "결제 고유번호")
    private String tid;

    @Schema(description = "가맹점 코드")
    private String cid;

    @Schema(description = "결제 상태 (READY: 결제 요청 / SEND_TMS: 결제 요청 메시지(TMS) 발송 완료 / OPEN_PAYMENT: 사용자가 카카오페이 결제 화면 진입 / SELECT_METHOD: 결제 수단 선택, 인증 완료 / ARS_WAITING: ARS: 인증 진행 중 / AUTH_PASSWORD:\t비밀번호 인증 완료 / ISSUED_SID: SID 발급 완료(정기 결제 시 SID만 발급 한 경우) / SUCCESS_PAYMENT: 결제 완료 / PART_CANCEL_PAYMENT: 부분 취소 / CANCEL_PAYMENT: 결제된 금액 모두 취소(부분 취소 여러 번으로 모두 취소된 경우 포함) / FAIL_AUTH_PASSWORD: 사용자 비밀번호 인증 실패 / QUIT_PAYMENT: 사용자가 결제 중단 / FAIL_PAYMENT: 결제 승인 실패)")
    private String status;

    @Schema(description = "가맹점 주문번호")
    private String partner_order_id;

    @Schema(description = "가맹점 회원 ID")
    private String partner_user_id;

    @Schema(description = "결제 수단 (CARD 또는 MONEY)")
    private String payment_method_type;

    @Schema(description = "결제 금액")
    private Amount amount;

    @Schema(description = "이번 요청으로 취소된 금액")
    private ApprovedCancelAmount approved_cancel_amount;

    @Schema(description = "상품 이름")
    private String item_name;

    @Schema(description = "상품 코드")
    private String item_code;

    @Schema(description = "상품 수량")
    private int quantity;

    @Schema(description = "결제 준비 요청 시각")
    private LocalDateTime create_at;

    @Schema(description = "결제 승인 시각")
    private LocalDateTime approved_at;

    @Schema(description = "결제 취소 시각")
    private LocalDateTime canceled_at;

    @Schema(description = "취소 요청 시 전달한 값")
    private String payload;

}
