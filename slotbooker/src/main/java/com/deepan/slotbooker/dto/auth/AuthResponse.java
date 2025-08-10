package com.deepan.slotbooker.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private List<String> roles;
}
