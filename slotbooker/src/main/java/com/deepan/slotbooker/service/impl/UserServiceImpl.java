package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.UserMapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse userRegisterService(UserRegisterRequest userRegisterRequest) {
        if (userRepository.findByMobileNumber(userRegisterRequest.getMobileNumber()).isPresent()) {
            throw new IllegalArgumentException("Mobile number already registered");
        }
        User user = UserMapper.createUserEntity(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.buildUserResponse(savedUser);
    }

    @Override
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.buildUserResponse(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserMapper::buildUserResponse).toList();
    }
}
