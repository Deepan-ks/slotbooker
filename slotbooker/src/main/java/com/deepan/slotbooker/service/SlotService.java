package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.slotDTO.SlotCreateRequest;
import com.deepan.slotbooker.dto.slotDTO.SlotResponse;
import com.deepan.slotbooker.dto.slotDTO.SlotUpdateRequest;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for Slot-related business logic.
 */
public interface SlotService {
    /**
     * Creates a new time slot for a facility.
     *
     * @param facilityId The ID of the facility for which the slot is created.
     * @param request DTO containing slot details.
     * @return SlotResponse DTO of the newly created slot.
     */
    @Transactional
    SlotResponse createSlot(Long facilityId, SlotCreateRequest request);

    /**
     * Retrieves all slots for a given facility, optionally within a time range.
     *
     * @param facilityId The ID of the facility.
     * @param start The start time for the search range (optional).
     * @param end The end time for the search range (optional).
     * @return A list of SlotResponse DTOs.
     */
    List<SlotResponse> getSlotsByFacility(Long facilityId, LocalDateTime start, LocalDateTime end);

    /**
     * Updates an existing slot.
     *
     * @param slotId The ID of the slot to update.
     * @param request DTO containing the updated slot details.
     * @return SlotResponse DTO of the updated slot.
     */
    @Transactional
    SlotResponse updateSlot(Long slotId, SlotUpdateRequest request);

    /**
     * Deletes a slot.
     *
     * @param slotId The ID of the slot to delete.
     * @return true if the slot was successfully deleted, false otherwise.
     */
    @Transactional
    Boolean deleteSlot(Long slotId);
}
