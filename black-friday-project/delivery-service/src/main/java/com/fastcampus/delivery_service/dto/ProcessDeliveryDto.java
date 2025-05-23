package com.fastcampus.delivery_service.dto;

public record ProcessDeliveryDto(
    Long orderId,
    String productName,
    Long productCount,
    String address
) {
    
}
