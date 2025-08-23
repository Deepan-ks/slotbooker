package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.slotDTO.SlotCreateRequest;
import com.deepan.slotbooker.dto.slotDTO.SlotResponse;
import com.deepan.slotbooker.dto.slotDTO.SlotUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import com.deepan.slotbooker.service.SlotService;
import jakarta.persistence.Temporal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;
    private final FacilityRepository facilityRepository;

    @Override
    @Transactional
    public SlotResponse createSlot(Long facilityId, SlotCreateRequest request) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        // Business logic: check for overlapping slots
        List<Slot> existingSlots = slotRepository.findByFacilityAndStartTimeBetween(facility, request.getStartTime(), request.getEndTime());
        if (!existingSlots.isEmpty()) {
            throw new IllegalArgumentException("An overlapping slot already exists for this facility.");
        }

        Slot newSlot = Slot.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isBooked(false)
                .facility(facility)
                .build();

        Slot savedSlot = slotRepository.save(newSlot);
        return Mapper.buildSlotResponse(savedSlot);
    }

    @Override
    public List<SlotResponse> getSlotsByFacility(Long facilityId, LocalDateTime start, LocalDateTime end) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        List<Slot> slots;
        if (start != null && end != null) {
            slots = slotRepository.findByFacilityAndStartTimeBetween(facility, start, end);
        } else {
            slots = slotRepository.findByFacility(facility);
        }

        return Mapper.buildSlotResponseList(slots);
    }

    @Override
    @Transactional
    public SlotResponse updateSlot(Long slotId, SlotUpdateRequest request) {
        Slot existingSlot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        if (request.getStartTime() != null) {
            existingSlot.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            existingSlot.setEndTime(request.getEndTime());
        }
        if (request.getIsBooked() != null) {
            existingSlot.setIsBooked(request.getIsBooked());
        }

        Slot updatedSlot = slotRepository.save(existingSlot);
        return Mapper.buildSlotResponse(updatedSlot);
    }

    @Override
    @Transactional
    public Boolean deleteSlot(Long slotId) {
        if (slotRepository.existsById(slotId)) {
            slotRepository.deleteById(slotId);
            return true;
        }
        return false;
    }
}
