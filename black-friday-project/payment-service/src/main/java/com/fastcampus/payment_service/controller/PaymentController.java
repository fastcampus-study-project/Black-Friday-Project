package com.fastcampus.payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.payment_service.dto.PaymentMethodDto;
import com.fastcampus.payment_service.dto.ProcessPaymentDto;
import com.fastcampus.payment_service.entity.Payment;
import com.fastcampus.payment_service.entity.PaymentMethod;
import com.fastcampus.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/methods")
    public PaymentMethod registerPaymentMethod(@RequestBody PaymentMethodDto dto) {
        return paymentService.registerPaymentMethod(dto.userId(), dto.paymentMethodType(), dto.creditCardNumber());
    }

    @PostMapping("/payment/process-payment")
    public Payment processPayment(@RequestBody ProcessPaymentDto dto) throws Exception {
        return paymentService.processPayment(dto.userId(), dto.orderId(), dto.amountKRW(), dto.paymentMethodId());
    }

    @GetMapping("/payment/users/{userId}/first-method")
    public PaymentMethod getPaymentMethod(@PathVariable Long userId) {
        return paymentService.getPaymentMethodByUser(userId);
    }

    @GetMapping("/payment/payments/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}
