package com.deepan.slotbooker.repository;


import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByVenue(Venue venue);
}
