package com.ddib.payment.order.controller;

import com.ddib.payment.order.dto.response.OrderResponseDto;
import com.ddib.payment.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문내역 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> viewOrderList(Principal principal) {
        List<OrderResponseDto> orderResponseDtoList = orderService.viewOrderList(principal);
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "주문내역 상세 조회 API")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> viewOrderDetail(@PathVariable String orderId, Principal principal) {
        OrderResponseDto orderResponseDto = orderService.viewOrderDetail(orderId, principal);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

}
