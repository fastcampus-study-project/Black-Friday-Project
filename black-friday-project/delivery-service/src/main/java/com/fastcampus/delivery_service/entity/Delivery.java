package com.fastcampus.delivery_service.entity;

import com.fastcampus.delivery_service.enums.DeliveryStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = { @Index(name = "idx_orderId", columnList = "orderId") })
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "delivery_id")
    public Long id;

    @Column(nullable = false, name = "order_id")
    public Long orderId;

    @Column(nullable = false, name = "product_name")
    public String productName;

    @Column(nullable = false, name = "product_count")
    public Long productCount;

    @Column(nullable = false, name = "address")
    public String address;

    @Column(nullable = false, name = "reference_code")
    public Long referenceCode;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    public DeliveryStatus status;

    @Builder
    protected Delivery(Long orderId, String productName, Long productCount, 
        String address, Long referenceCode, DeliveryStatus status
    ) {
        this.orderId = orderId;
        this.productName = productName;
        this.productCount = productCount;
        this.address = address;
        this.referenceCode = referenceCode;
        this.status = status;
    }
}
