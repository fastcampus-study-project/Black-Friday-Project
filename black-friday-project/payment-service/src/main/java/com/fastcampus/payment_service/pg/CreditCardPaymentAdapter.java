package com.fastcampus.payment_service.pg;

public interface CreditCardPaymentAdapter {
    Long processCreditCardPayment(Long amountKRW, String creditCardNumber);
}
