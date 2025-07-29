package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByPlayer(User player);
}
