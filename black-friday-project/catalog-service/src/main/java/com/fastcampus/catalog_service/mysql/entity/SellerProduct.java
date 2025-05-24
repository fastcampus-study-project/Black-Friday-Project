package com.fastcampus.catalog_service.mysql.entity;

import jakarta.persistence.*;

@Entity
public class SellerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long sellerId;

    public SellerProduct() {
    }

    public SellerProduct(Long sellerId) {
        this.sellerId = sellerId;
    }

}