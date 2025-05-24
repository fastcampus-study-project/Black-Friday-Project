package com.fastcampus.catalog_service.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.catalog_service.mysql.entity.SellerProduct;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {
    List<SellerProduct> findBySellerId(Long sellerId);
}
