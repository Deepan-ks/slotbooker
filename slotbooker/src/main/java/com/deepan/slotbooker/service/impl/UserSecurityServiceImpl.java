package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
        User user = userRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("User not found with mobile: " + mobileNumber));
        return new UserSecurity(user);
    }
}
