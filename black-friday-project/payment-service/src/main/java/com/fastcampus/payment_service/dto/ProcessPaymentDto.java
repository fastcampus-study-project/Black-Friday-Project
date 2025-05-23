package com.fastcampus.payment_service.dto;

public record ProcessPaymentDto(
    Long userId,
    Long orderId,
    Long amountKRW,
    Long paymentMethodId
) {
    
}
