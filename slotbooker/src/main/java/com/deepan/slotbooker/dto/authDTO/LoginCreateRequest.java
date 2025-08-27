package com.deepan.slotbooker.dto.authDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateRequest {

    @NotEmpty
    private String mobileNumber;

    @NotEmpty
    private String password;
}
