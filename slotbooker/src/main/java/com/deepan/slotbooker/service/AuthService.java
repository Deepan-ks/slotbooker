package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.authDTO.LoginCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import jakarta.transaction.Transactional;

import java.util.Map;

public interface AuthService {
    /**
     * Registers a new user with the application.
     *
     * @param userCreateRequest DTO containing user registration details.
     * @return UserResponse DTO of the newly registered user.
     */
    @Transactional
    UserResponse registerUser(UserCreateRequest userCreateRequest);

    Map<String, String> login(LoginCreateRequest request);
}
