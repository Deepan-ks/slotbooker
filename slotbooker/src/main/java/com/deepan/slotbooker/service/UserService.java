package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.model.User;

import java.util.List;

public interface UserService {
    UserResponse userRegisterService(UserRegisterRequest userRegisterRequest);
    UserResponse findUserById(Long userId);
    List<UserResponse> findAllUsers();
}
