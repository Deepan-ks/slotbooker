package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.userDTO.UserCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import com.deepan.slotbooker.dto.userDTO.UserUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Constructor-based dependency injection.
     * Spring will automatically provide the required beans (UserRepository and PasswordEncoder)
     * when creating an instance of this class.
     *
     * userRepository The repository for User entities.
     * passwordEncoder The PasswordEncoder bean for password hashing.
     */
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserCreateRequest request) {
        // Business logic: check for existing user before saving
        if (userRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .userStatus(request.getUserStatus())
                .build();

        User savedUser = userRepository.save(user);
        return Mapper.buildUserResponse(savedUser);
    }

    @Override
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return Mapper.buildUserResponse(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return Mapper.buildUserResponseList(users);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getName() != null) {
            existingUser.setName(request.getName());
        }
        if (request.getBirthDate() != null) {
            existingUser.setBirthDate(request.getBirthDate());
        }
        if (request.getEmail() != null) {
            existingUser.setEmail(request.getEmail());
        }
        if (request.getMobileNumber() != null) {
            existingUser.setMobileNumber(request.getMobileNumber());
        }
        if (request.getUserStatus() != null) {
            existingUser.setUserStatus(request.getUserStatus());
        }

        User updatedUser = userRepository.save(existingUser);
        return Mapper.buildUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public Boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
