package com.fastcampus.member_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String userName;

    @Builder
    protected UserEntity(String loginId, String userName) {
        this.loginId = loginId;
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
