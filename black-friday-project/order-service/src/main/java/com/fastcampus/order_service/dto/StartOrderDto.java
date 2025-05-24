package com.fastcampus.order_service.dto;

public record StartOrderDto(
    Long userId,
    Long productId,
    Long count
) {
    
}
