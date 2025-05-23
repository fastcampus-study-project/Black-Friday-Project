package com.fastcampus.payment_service.service;

import org.springframework.stereotype.Service;

import com.fastcampus.payment_service.entity.Payment;
import com.fastcampus.payment_service.entity.PaymentMethod;
import com.fastcampus.payment_service.enums.PaymentMethodType;
import com.fastcampus.payment_service.enums.PaymentStatus;
import com.fastcampus.payment_service.pg.CreditCardPaymentAdapter;
import com.fastcampus.payment_service.repository.PaymentMethodRepository;
import com.fastcampus.payment_service.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentRepository paymentRepository;
    private final CreditCardPaymentAdapter creditCardPaymentAdapter;

    public PaymentMethod registerPaymentMethod(
        Long userId,
        PaymentMethodType paymentMethodType,
        String creditCardNumber
    ) {
        var paymentMethod = PaymentMethod.builder()
            .userId(userId)
            .paymentMethodType(paymentMethodType)
            .creditCardNumber(creditCardNumber)
            .build();
        return paymentMethodRepository.save(paymentMethod);
    }

    public Payment processPayment(
        Long userId,
        Long orderId,
        Long amountKRW,
        Long paymentMethodId
    ) throws Exception {
        var paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow();

        if (paymentMethod.paymentMethodType != PaymentMethodType.CREDIT_CARD) {
            throw new Exception("Unsupported payment method type");
        }

        var refCode = creditCardPaymentAdapter.processCreditCardPayment(amountKRW, paymentMethod.creditCardNumber);

        var payment = Payment.builder()
                .userId(userId)
                .orderId(orderId)
                .amountKRW(amountKRW)
                .paymentMethodType(paymentMethod.paymentMethodType)
                .paymentData(paymentMethod.creditCardNumber)
                .paymentStatus(PaymentStatus.COMPLETED)
                .referenceCode(refCode)
                .build();

        return paymentRepository.save(payment);
    }

    public PaymentMethod getPaymentMethodByUser(Long userId) {
        return paymentMethodRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow();
    }
}
