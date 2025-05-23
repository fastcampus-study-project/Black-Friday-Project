package com.fastcampus.member_service.service;

import org.springframework.stereotype.Service;

import com.fastcampus.member_service.entity.UserEntity;
import com.fastcampus.member_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity registerUser(String loginId, String userName) {
        var user = UserEntity.builder()
            .loginId(loginId)
            .userName(userName)
            .build();
        return userRepository.save(user);
    }

    public UserEntity modifyUser(Long userId, String userName) {
        var user = userRepository.findById(userId).orElseThrow();
        user.setUserName(userName);

        return userRepository.save(user);
    }

    public UserEntity getUser(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow();
    }
}
