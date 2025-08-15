package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.model.enums.Role;
import com.deepan.slotbooker.model.User;

public class UserMapper {

    private UserMapper() {}

    /**
     * Converts registration request into a User entity.
     */
    public static User createUserEntity(UserRegisterRequest request) {
        return User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .userRole(request.getUserRole())
                // password encoding will be handled in service layer
                .build();
    }

    /**
     * Converts a User entity into response DTO.
     */
    public static UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .userRole(user.getUserRole())
                .build();
    }
}
