package com.deepan.slotbooker.dto.auth;

import com.deepan.slotbooker.model.enums.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private String mobile;
    private String userName;
    private String password;
    private Role role;
}
