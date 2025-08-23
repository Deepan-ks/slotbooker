package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Venue entity.
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByOwnerId(Long ownerId);
    List<Venue> findByCity(String city);
}
