package com.fastcampus.delivery_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.delivery_service.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUserId(Long userId);
}
