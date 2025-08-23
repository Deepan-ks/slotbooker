package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Booking entity.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByPlayer(User player);
}
