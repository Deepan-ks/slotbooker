package com.deepan.slotbooker.service;

import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.model.enums.Role;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.impl.UserSecurityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityServiceImpl userSecurityService;

    @Test
    void testLoadUserByUsername_Success(){
        String mobileNumber = "9630258741";
        User user = new User(1L, "John", "password123", "john@example.com", mobileNumber, Role.PLAYER);

        when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(user));

        UserDetails userDetails = userSecurityService.loadUserByUsername(mobileNumber);

        assertNotNull(userDetails);
        assertEquals(user.getUserName(), userDetails.getUsername());
        verify(userRepository).findByMobileNumber(mobileNumber);
    }

    @Test
    void testLoadUserByUsername_NotFound(){
        String mobileNumber = "9630258741";

        when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userSecurityService.loadUserByUsername(mobileNumber));
    }
}
