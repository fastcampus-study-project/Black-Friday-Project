package com.fastcampus.order_service.entity;

import com.fastcampus.order_service.enums.OrderStatus;

import jakarta.persistence.*;

@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long userId;
    public Long productId; // 단일 상품에 대해서만 주문 가능하다고 가정
    public Long count;

    public OrderStatus orderStatus;
    public Long paymentId;
    public Long deliveryId;
    public String deliveryAddress;

    public ProductOrder() {
    }

    public ProductOrder(Long userId, Long productId, Long count, OrderStatus orderStatus, Long paymentId,
            Long deliveryId, String deliveryAddress) {
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.orderStatus = orderStatus;
        this.paymentId = paymentId;
        this.deliveryId = deliveryId;
        this.deliveryAddress = deliveryAddress;
    }
}
