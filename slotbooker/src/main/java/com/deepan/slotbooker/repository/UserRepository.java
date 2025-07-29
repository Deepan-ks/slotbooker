package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,  Long> {

    boolean existsByMobileNumber(String mobileNumber);

    Optional<User> findByMobileNumber(String mobileNumber);
}
