package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.slot.SlotCreateRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.SlotMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import com.deepan.slotbooker.service.SlotService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private SlotRepository slotRepository;


    @Override
    @Transactional
    public SlotResponse createSlot(Long facilityId, SlotCreateRequest request) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        Slot newSlot = SlotMapper.createSlotEntity(request, facility);
        Slot savedSlot = slotRepository.save(newSlot);
        return SlotMapper.buildSlotResponse(savedSlot);
    }

    @Override
    public List<SlotResponse> getSlotByFacility(Long facilityId, String start, String end) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        List<Slot> slots;

        if (start != null && end != null) {
            LocalDateTime startTime = LocalDateTime.parse(start);
            LocalDateTime endTime = LocalDateTime.parse(end);

            slots = slotRepository.findByFacilityAndStartTimeBetween(facility, startTime, endTime);
        } else {
            slots = slotRepository.findByFacility(facility);
        }

        return slots.stream().map(SlotMapper::buildSlotResponse).toList();
    }

}
