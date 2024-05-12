package com.ddib.payment.order.controller;

import com.ddib.payment.order.dto.response.OrderResponseDto;
import com.ddib.payment.order.dto.response.SalesHistoryResponseDto;
import com.ddib.payment.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문내역 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponseDto>> viewOrderList(@PathVariable int userId) {
        log.info("===== 주문내역 목록 조회 API 시작 =====");
        List<OrderResponseDto> orderResponseDtoList = orderService.viewOrderList(userId);
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "주문내역 상세 조회 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/detail/{orderId}")
    public ResponseEntity<OrderResponseDto> viewOrderDetail(@PathVariable String orderId) {
        log.info("===== 주문내역 상세 조회 API 시작 =====");
        OrderResponseDto orderResponseDto = orderService.viewOrderDetail(orderId);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "기업회원 판매내역 상세 조회 API", description = "기업회원이 등록한 상품에 대한 주문내역을 보여줍니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/sales-history/{productId}")
    public ResponseEntity<SalesHistoryResponseDto> viewSalesHistory(@PathVariable int productId) {
        SalesHistoryResponseDto salesHistoryResponseDto = orderService.viewSalesHistory(productId);
        return new ResponseEntity<>(salesHistoryResponseDto, HttpStatus.OK);
    }

}
