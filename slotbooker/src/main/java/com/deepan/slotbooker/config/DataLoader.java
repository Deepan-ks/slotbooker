package com.deepan.slotbooker.config;

import com.deepan.slotbooker.model.*;
import com.deepan.slotbooker.model.enums.Role;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, VenueRepository venueRepository, SportRepository sportRepository, FacilityRepository facilityRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                // load users
                userRepository.save(User.builder()
                        .userName("Deepan")
                        .email("deepan@example.com")
                        .mobileNumber("9876543210")
                        .password(new BCryptPasswordEncoder().encode("password123"))
                        .userRole(Role.OWNER)
                        .build());

                userRepository.save(User.builder()
                        .userName("Ravi")
                        .email("ravi@example.com")
                        .mobileNumber("9123456789")
                        .password(new BCryptPasswordEncoder().encode("test123"))
                        .userRole(Role.PLAYER)
                        .build());

                // load venue
                Venue arenaX = Venue.builder()
                        .venueName("ArenaX Sports Club")
                        .location("Coimbatore")
                        .ownerId(userRepository.findByMobileNumber("9876543210").orElseThrow())
                        .build();

                venueRepository.save(arenaX);

                // load sports
                List<String> sportNames = List.of("Football", "Cricket", "Swimming", "Badminton");
                for (String sportName : sportNames) {
                    if (!sportRepository.existsBySportName(sportName)) {
                        sportRepository.save(Sport.builder().sportName(sportName).build());
                    }
                }
            }
        };

    }




}
