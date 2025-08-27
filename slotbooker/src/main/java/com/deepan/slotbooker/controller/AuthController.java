package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.authDTO.LoginCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserCreateRequest;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import com.deepan.slotbooker.security.CustomUserDetailsService;
import com.deepan.slotbooker.security.JwtUtil;
import com.deepan.slotbooker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil  jwtUtil;
    private final UserService userService;

    /**
     * Handles user login and generates a JWT.
     *
     * @param request The login request body containing mobileNumber and password.
     * @return A ResponseEntity with the generated JWT if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginCreateRequest request) {
        String mobileNumber = request.getMobileNumber();
        String password = request.getPassword();

        try {
            // Authenticate the user with the provided credentials.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobileNumber, password));
        } catch (BadCredentialsException e) {
            // Throw an exception if authentication fails.
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        // If authentication is successful, load the full user details.
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(mobileNumber);

        // Generate the JWT using the user details.
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return the JWT in the response body.
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return ResponseEntity.ok(response);
    }

    /**
     * Registers a new user.
     *
     * @param request The DTO containing user creation details.
     * @return A ResponseEntity with the newly created user and HTTP status 201 (Created).
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserResponse response = userService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
