package com.fastcampus.delivery_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.delivery_service.entity.Delivery;
import com.fastcampus.delivery_service.enums.DeliveryStatus;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByOrderId(Long orderId);

    List<Delivery> findAllByStatus(DeliveryStatus status);
}
