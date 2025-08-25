package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.userDTO.UserCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import com.deepan.slotbooker.dto.userDTO.UserUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing User-related API endpoints.
 * All endpoints are now prefixed with "/api/v1".
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Registers a new user.
     *
     * @param request The DTO containing user creation details.
     * @return A ResponseEntity with the newly created user and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserResponse response = userService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return A ResponseEntity with the user's details and HTTP status 200 (OK), or 404 (Not Found).
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        try {
            UserResponse response = userService.findUserById(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all users.
     *
     * @return A ResponseEntity with a list of all users and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Updates an existing user.
     *
     * @param userId The ID of the user to update.
     * @param request The DTO with fields to update.
     * @return A ResponseEntity with the updated user and HTTP status 200 (OK), or 404 (Not Found).
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        try {
            UserResponse response = userService.updateUser(userId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a user.
     *
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) on success, or 404 (Not Found).
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Boolean isDeleted = userService.deleteUser(userId);
        if (Boolean.TRUE.equals(isDeleted)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
