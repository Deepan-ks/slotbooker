package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Get all users
     * @return
     */
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Register a new user
     * @param registerRequest
     * @return
     */
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest registerRequest){
        UserResponse response = userService.userRegisterService(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get user by id
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}
