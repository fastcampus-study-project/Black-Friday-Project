package com.fastcampus.payment_service.entity;

import com.fastcampus.payment_service.enums.PaymentMethodType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = { @Index(name = "idx_userId", columnList = "userId") })
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "payment_method_id")
    public Long id;

    @Column(nullable = false, name = "user_id")
    public Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public PaymentMethodType paymentMethodType;

    @Column(nullable = false)
    public String creditCardNumber;

    @Builder
    protected PaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }
}
