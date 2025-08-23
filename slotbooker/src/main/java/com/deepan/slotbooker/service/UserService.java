package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.userDTO.UserCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import com.deepan.slotbooker.dto.userDTO.UserUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for User-related business logic.
 */
public interface UserService {

    /**
     * Registers a new user with the application.
     *
     * @param userCreateRequest DTO containing user registration details.
     * @return UserResponse DTO of the newly registered user.
     */
    @Transactional
    UserResponse registerUser(UserCreateRequest userCreateRequest);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return UserResponse DTO.
     */
    UserResponse findUserById(Long userId);

    /**
     * Retrieves all users.
     *
     * @return A list of UserResponse DTOs.
     */
    List<UserResponse> findAllUsers();

    /**
     * Updates an existing user.
     *
     * @param userId The ID of the user to update.
     * @param request DTO containing the updated user details.
     * @return UserResponse DTO of the updated user.
     */
    @Transactional
    UserResponse updateUser(Long userId, UserUpdateRequest request);

    /**
     * Deletes a user.
     *
     * @param userId The ID of the user to delete.
     * @return true if the user was successfully deleted, false otherwise.
     */
    @Transactional
    Boolean deleteUser(Long userId);
}
