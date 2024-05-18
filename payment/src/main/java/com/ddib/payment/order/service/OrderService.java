package com.ddib.payment.order.service;

import com.ddib.payment.order.domain.Order;
import com.ddib.payment.order.dto.SalesHistory;
import com.ddib.payment.order.dto.response.OrderResponseDto;
import com.ddib.payment.order.dto.response.SalesHistoryResponseDto;
import com.ddib.payment.order.repository.OrderRepository;
import com.ddib.payment.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public List<OrderResponseDto> viewOrderList(int userId) {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Order> orderList = orderRepository.findAllByUserId(userId);
        for(Order order : orderList) {
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .orderId(order.getOrderId())
                    .orderDate(sdf.format(order.getOrderDate().toLocalDateTime().plusHours(9)))
                    .status(order.getStatus().getStatus())
                    .companyName(order.getProduct().getSeller().getCompanyName())
                    .thumbnailImage(order.getProduct().getThumbnailImage())
                    .productId(order.getProduct().getProductId())
                    .productName(order.getProduct().getName())
                    .quantity(order.getProductCount())
                    .price(order.getProduct().getPrice())
                    .totalAmount(order.getTotalPrice())
                    .receiverName(order.getReceiverName())
                    .receiverPhone(order.getReceiverPhone())
                    .orderRoadAddress(order.getOrderRoadAddress())
                    .orderDetailAddress(order.getOrderDetailAddress())
                    .orderZipcode(order.getOrderZipcode())
                    .paymentMethod(paymentRepository.findByOrderId(order.getOrderId()).getPaymentMethodType())
                    .build();
            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }

    public OrderResponseDto viewOrderDetail(String orderId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Order order = orderRepository.findByOrderId(orderId);
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .orderId(orderId)
                .orderDate(sdf.format(order.getOrderDate().toLocalDateTime().plusHours(9)))
                .status(order.getStatus().getStatus())
                .companyName(order.getProduct().getSeller().getCompanyName())
                .thumbnailImage(order.getProduct().getThumbnailImage())
                .productId(order.getProduct().getProductId())
                .productName(order.getProduct().getName())
                .quantity(order.getProductCount())
                .price(order.getProduct().getPrice())
                .totalAmount(order.getTotalPrice())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .orderRoadAddress(order.getOrderRoadAddress())
                .orderDetailAddress(order.getOrderDetailAddress())
                .orderZipcode(order.getOrderZipcode())
                .paymentMethod(paymentRepository.findByOrderId(order.getOrderId()).getPaymentMethodType())
                .build();
        return orderResponseDto;
    }

    public SalesHistoryResponseDto viewSalesHistory(int productId) {
        List<Order> orderList = orderRepository.findAllByProductId(productId);
        List<SalesHistory> salesHistoryList = new ArrayList<>();
        for(Order order : orderList) {
            SalesHistory salesHistory = SalesHistory.builder()
                    .orderId(order.getOrderId())
                    .name(order.getReceiverName())
                    .phone(order.getReceiverPhone())
                    .address(order.getOrderRoadAddress() + " " + order.getOrderDetailAddress() + " (" + order.getOrderZipcode() + ")")
                    .build();
            salesHistoryList.add(salesHistory);
        }

        return SalesHistoryResponseDto.builder()
                .productId(productId)
                .totalSoldCount(salesHistoryList.size())
                .salesHistoryList(salesHistoryList)
                .build();
    }

}
