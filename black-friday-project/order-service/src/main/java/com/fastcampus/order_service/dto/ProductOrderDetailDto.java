package com.fastcampus.order_service.dto;

import com.fastcampus.order_service.enums.OrderStatus;

public record ProductOrderDetailDto(
    Long id,
    Long userId,
    Long productId,
    Long paymentId,
    Long deliveryId,
    OrderStatus orderStatus,
    String paymentStatus,
    String deliveryStatus
) {
    
}
