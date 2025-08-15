package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.auth.AuthRequest;
import com.deepan.slotbooker.dto.auth.AuthResponse;
import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.security.jwt.JwtTokenProvider;
import com.deepan.slotbooker.service.UserService;
import com.deepan.slotbooker.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getMobile(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return ResponseEntity.ok(new AuthResponse(token, roles));
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    /**
     * Register a new user
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest registerRequest){
        UserResponse response = userService.userRegisterService(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
