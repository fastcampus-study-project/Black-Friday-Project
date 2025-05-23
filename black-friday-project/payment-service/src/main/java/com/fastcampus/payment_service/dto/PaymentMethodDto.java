package com.fastcampus.payment_service.dto;

import com.fastcampus.payment_service.enums.PaymentMethodType;

public record PaymentMethodDto(
    Long userId, 
    PaymentMethodType paymentMethodType, 
    String creditCardNumber
) {
    
}
