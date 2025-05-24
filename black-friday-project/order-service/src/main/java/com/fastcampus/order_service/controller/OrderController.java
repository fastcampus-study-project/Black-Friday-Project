package com.fastcampus.order_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.order_service.dto.FinishOrderDto;
import com.fastcampus.order_service.dto.ProductOrderDetailDto;
import com.fastcampus.order_service.dto.StartOrderDto;
import com.fastcampus.order_service.dto.StartOrderResponseDto;
import com.fastcampus.order_service.entity.ProductOrder;
import com.fastcampus.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/start-order")
    public StartOrderResponseDto startOrder(@RequestBody StartOrderDto dto) throws Exception {
        return orderService.startOrder(dto.userId(), dto.productId(), dto.count());
    }

    @PostMapping("/order/finish-order")
    public ProductOrder finishOrder(@RequestBody FinishOrderDto dto) throws Exception {
        return orderService.finishOrder(dto.orderId(), dto.paymentMethodId(), dto.addressId());
    }

    @GetMapping("/order/users/{userId}/orders")
    public List<ProductOrder> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/order/orders/{orderId}")
    public ProductOrderDetailDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }
}
