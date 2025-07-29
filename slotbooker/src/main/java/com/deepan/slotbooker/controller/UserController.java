package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.UserDTO;
import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.UserMapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<UserDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserMapper::mapToUserDTO).toList();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest registerRequest){
        User user = UserMapper.createUserEntity(registerRequest);
        User savedUser = userRepository.save(user);
        UserResponse response = UserMapper.buildUserResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(UserMapper.buildUserResponse(user));
    }
}
