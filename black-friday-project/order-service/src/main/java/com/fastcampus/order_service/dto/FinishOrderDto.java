package com.fastcampus.order_service.dto;

public record FinishOrderDto(
    Long orderId,
    Long paymentMethodId,
    Long addressId
) {
    
}
