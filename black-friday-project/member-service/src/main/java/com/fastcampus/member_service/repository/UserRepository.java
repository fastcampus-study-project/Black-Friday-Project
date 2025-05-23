package com.fastcampus.member_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.member_service.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginId(String loginId);
}
