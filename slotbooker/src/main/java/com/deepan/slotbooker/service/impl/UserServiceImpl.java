package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.UserMapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse userRegisterService(UserRegisterRequest userRegisterRequest) {
        User user = UserMapper.createUserEntity(userRegisterRequest);
        User savedUser = userRepository.save(user);
        UserResponse response = UserMapper.buildUserResponse(savedUser);
        return response;
    }

    @Override
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.buildUserResponse(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = userList.stream().map(UserMapper::buildUserResponse).toList();
        return userResponseList;
    }
}
