package com.ddib.payment.order.service;

import com.ddib.payment.order.domain.Order;
import com.ddib.payment.order.dto.response.OrderResponseDto;
import com.ddib.payment.order.repository.OrderRepository;
import com.ddib.payment.payment.repository.PaymentRepository;
import com.ddib.payment.user.domain.User;
import com.ddib.payment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public List<OrderResponseDto> viewOrderList(Principal principal) {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        User user = userRepository.findByEmail(principal.getName());
        User user = userRepository.findByEmail("tpwls101@naver.com");
        List<Order> orderList = orderRepository.findAllByUserId(user.getUserId());
        for(Order order : orderList) {
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .orderId(order.getOrderId())
                    .orderDate(sdf.format(order.getOrderDate()))
                    .status(order.getStatus().getStatus())
                    .companyName(order.getProduct().getSeller().getCompanyName())
                    .thumbnailImage(order.getProduct().getThumbnailImage())
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
                .orderDate(sdf.format(order.getOrderDate()))
                .status(order.getStatus().getStatus())
                .companyName(order.getProduct().getSeller().getCompanyName())
                .thumbnailImage(order.getProduct().getThumbnailImage())
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

}
