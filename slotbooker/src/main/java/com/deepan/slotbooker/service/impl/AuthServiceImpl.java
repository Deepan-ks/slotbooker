package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.authDTO.LoginCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.security.CustomUserDetailsService;
import com.deepan.slotbooker.security.JwtUtil;
import com.deepan.slotbooker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

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
    public Map<String, String> login(LoginCreateRequest request) {
        String mobileNumber = request.getMobileNumber();
        String password = request.getPassword();

        try {
            // Authenticate the user with the provided credentials.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobileNumber, password));
        } catch (BadCredentialsException e) {
            // Throw an exception if authentication fails.
            throw new BadCredentialsException("Incorrect username or password");
        }

        // If authentication is successful, load the full user details.
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(mobileNumber);

        // Generate the JWT using the user details.
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return the JWT in the response body.
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return response;
    }
}
