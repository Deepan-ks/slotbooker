package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.slotDTO.SlotCreateRequest;
import com.deepan.slotbooker.dto.slotDTO.SlotResponse;
import com.deepan.slotbooker.dto.slotDTO.SlotUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.service.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for managing Slot-related API endpoints.
 * All endpoints are now prefixed with "/api/v1".
 */
@RestController
@RequestMapping("/api/v1/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    /**
     * Creates a new slot for a facility.
     *
     * @param facilityId The ID of the facility.
     * @param request The DTO with slot creation details.
     * @return A ResponseEntity with the created slot and HTTP status 201 (Created).
     */
    @PostMapping("/{facilityId}")
    public ResponseEntity<SlotResponse> createSlot(@PathVariable Long facilityId, @Valid @RequestBody SlotCreateRequest request) {
        try {
            SlotResponse response = slotService.createSlot(facilityId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all slots for a facility, optionally filtered by date range.
     *
     * @param facilityId The ID of the facility.
     * @param startTime The start time of the range (optional).
     * @param endTime The end time of the range (optional).
     * @return A ResponseEntity with a list of slots and HTTP status 200 (OK).
     */
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<SlotResponse>> getSlotsByFacility(
            @PathVariable Long facilityId,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime) {
        try {
            List<SlotResponse> slots = slotService.getSlotsByFacility(facilityId, startTime, endTime);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing slot.
     *
     * @param slotId The ID of the slot to update.
     * @param request The DTO with fields to update.
     * @return A ResponseEntity with the updated slot and HTTP status 200 (OK), or 404 (Not Found).
     */
    @PutMapping("/{slotId}")
    public ResponseEntity<SlotResponse> updateSlot(@PathVariable Long slotId, @Valid @RequestBody SlotUpdateRequest request) {
        try {
            SlotResponse response = slotService.updateSlot(slotId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a slot.
     *
     * @param slotId The ID of the slot to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) on success, or 404 (Not Found).
     */
    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long slotId) {
        Boolean isDeleted = slotService.deleteSlot(slotId);
        if (Boolean.TRUE.equals(isDeleted)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
