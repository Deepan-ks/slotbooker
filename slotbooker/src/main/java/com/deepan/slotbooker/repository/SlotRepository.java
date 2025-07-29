package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByFacility(Facility facility);
    List<Slot> findByFacilityAndStartTimeBetween(Facility facility, LocalDateTime startTime, LocalDateTime endTime);
}
