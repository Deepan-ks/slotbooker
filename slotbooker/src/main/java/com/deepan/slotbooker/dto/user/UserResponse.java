package com.deepan.slotbooker.dto.user;

import com.deepan.slotbooker.model.enums.Role;
import lombok.*;

@Value
@Builder
public class UserResponse {
    Long userId;
    String userName;
    String email;
    Role userRole;
    String mobileNumber;
}
