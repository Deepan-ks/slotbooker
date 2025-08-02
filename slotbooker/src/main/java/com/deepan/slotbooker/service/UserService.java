package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    /**
     * Create a new user
     * @param userRegisterRequest
     * @return
     */
    UserResponse userRegisterService(UserRegisterRequest userRegisterRequest);

    /**
     * find user by id
     * @param userId
     * @return
     */
    UserResponse findUserById(Long userId);

    /**
     * find all users
     * @return
     */
    List<UserResponse> findAllUsers();
}
