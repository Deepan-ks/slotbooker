package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.SlotDTO;
import com.deepan.slotbooker.dto.slot.SlotCreateRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.SlotMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/facilities/{facilityId}/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotRepository slotRepository;
    private final FacilityRepository facilityRepository;

    @PostMapping
    public ResponseEntity<SlotResponse> createSlot (@PathVariable Long facilityId, @Valid @RequestBody SlotCreateRequest request) {

        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        Slot newSlot = SlotMapper.createSlotEntity(request, facility);
        Slot savedSlot = slotRepository.save(newSlot);
        SlotResponse response = SlotMapper.buildSlotResponse(savedSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SlotResponse>> fetchSlotsByFacility(@PathVariable Long facilityId,
                                                                   @RequestParam(required = false) String start,
                                                                   @RequestParam(required = false) String end) {

        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        List<Slot> slots;

        if (start != null && end != null) {
            LocalDateTime startTime = LocalDateTime.parse(start);
            LocalDateTime endTime = LocalDateTime.parse(end);

            slots = slotRepository.findByFacilityAndStartTimeBetween(facility, startTime, endTime);
        } else {
            slots = slotRepository.findByFacility(facility);
        }

        List<SlotResponse> responses = slots.stream().map(SlotMapper::buildSlotResponse).toList();

        return ResponseEntity.ok(responses);
    }

}
