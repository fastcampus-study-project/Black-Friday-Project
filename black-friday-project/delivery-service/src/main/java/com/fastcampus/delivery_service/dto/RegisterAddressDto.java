package com.fastcampus.delivery_service.dto;

public record RegisterAddressDto(
    Long userId,
    String address,
    String alias
) {
    
}
