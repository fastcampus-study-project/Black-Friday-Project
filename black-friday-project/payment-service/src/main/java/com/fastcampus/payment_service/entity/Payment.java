package com.fastcampus.payment_service.entity;

import com.fastcampus.payment_service.enums.PaymentMethodType;
import com.fastcampus.payment_service.enums.PaymentStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = { @Index(name = "idx_userId", columnList = "userId") })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "payment_id")
    public Long id;

    @Column(name = "user_id")
    public Long userId;

    @Column(nullable = false, unique = true)
    public Long orderId;

    @Column(nullable = false, name = "amount_krw")
    public Long amountKRW;

    @Column(nullable = false, name = "payment_method_type")
    @Enumerated(EnumType.STRING)
    public PaymentMethodType paymentMethodType;

    @Column(nullable = false, name = "payment_data")
    public String paymentData; // <- credit card 번호

    @Column(nullable = false, name = "payment_status")
    @Enumerated(EnumType.STRING)
    public PaymentStatus paymentStatus;

    @Column(nullable = false, unique = true)
    public Long referenceCode;

    @Builder
    protected Payment(
        Long userId, Long orderId, Long amountKRW, PaymentMethodType paymentMethodType, 
        String paymentData, PaymentStatus paymentStatus, Long referenceCode
    ) {
        this.userId = userId;
        this.orderId = orderId;
        this.amountKRW = amountKRW;
        this.paymentMethodType = paymentMethodType;
        this.paymentData = paymentData;
        this.paymentStatus = paymentStatus;
        this.referenceCode = referenceCode;
    }
}
