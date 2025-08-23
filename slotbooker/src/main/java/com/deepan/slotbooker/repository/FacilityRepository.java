package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Facility entity.
 */
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByVenue(Venue venue);
}
