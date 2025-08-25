package com.deepan.slotbooker.dto.userDTO;

import com.deepan.slotbooker.model.enums.Roles;
import com.deepan.slotbooker.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String mobileNumber;
    private Roles role;
    private UserStatus userStatus;
}
