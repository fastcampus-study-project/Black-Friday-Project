package com.fastcampus.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.order_service.entity.ProductOrder;

public interface OrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByUserId(Long userId);
}
