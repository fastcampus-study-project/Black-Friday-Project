package com.fastcampus.order_service.dto;

public record ProcessPaymentDto(
    Long orderId,
    Long userId,
    Long amountKRW,
    Long paymentMethodId
) {
    
}
