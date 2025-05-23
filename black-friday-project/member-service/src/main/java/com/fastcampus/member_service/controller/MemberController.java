package com.fastcampus.member_service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.member_service.dto.ModifyUserDto;
import com.fastcampus.member_service.dto.RegisterUserDto;
import com.fastcampus.member_service.entity.UserEntity;
import com.fastcampus.member_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final UserService userService;

    @PostMapping("/member/users/registration")
    public UserEntity registerUser(@RequestBody RegisterUserDto dto) {
        return userService.registerUser(dto.loginId(), dto.userName());
    }

    @PutMapping("/member/users/{userId}/modify")
    public UserEntity modifyUser(@PathVariable Long userId, @RequestBody ModifyUserDto dto) {
        return userService.modifyUser(userId, dto.userName());
    }

    @PostMapping("/member/users/{loginId}/login")
    public UserEntity login(@PathVariable String loginId) {
        return userService.getUser(loginId);
    }

}
