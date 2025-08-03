package com.deepan.slotbooker.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthRequest {

    @NotBlank
    private String mobile;

    @NotBlank
    private String password;
}
