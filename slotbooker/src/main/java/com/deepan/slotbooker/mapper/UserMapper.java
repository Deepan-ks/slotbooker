package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.UserDTO;
import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.model.Role;
import com.deepan.slotbooker.model.User;

public class UserMapper {

    /**
     * Builds an internal UserDTO for cross-layer communication or legacy use.
     */
    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getUserId())
                .name(user.getUserName())
                .mobile(user.getMobileNumber())
                .email(user.getEmail())
                .role(user.getUserRole().name())
                .build();
    }

    /**
     * Constructs a User entity from UserDTO (used internally if needed).
     */
    public static User mapToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobile());
        // Role and password set elsewhere (registration logic)
        return user;
    }

    /**
     * Converts registration request into a User entity.
     */
    public static User createUserEntity(UserRegisterRequest request) {
        return User.builder()
                .userName(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // for now, plain-text or hashed later
                .userRole(Role.valueOf(request.getRole()))
                .mobileNumber(request.getMobile())
                .build();
    }

    /**
     * Converts a User entity into response DTO.
     */
    public static UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .name(user.getUserName())
                .email(user.getEmail())
                .role(user.getUserRole().name())
                .mobile(user.getMobileNumber())
                .build();
    }
}
