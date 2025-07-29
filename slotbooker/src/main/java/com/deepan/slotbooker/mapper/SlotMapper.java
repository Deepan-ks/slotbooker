package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.SlotDTO;
import com.deepan.slotbooker.dto.slot.SlotCreateRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;

public class SlotMapper {

    /**
     * Builds an internal SlotDTO for cross-layer communication or legacy use.
     */
    public static SlotDTO mapToSlotDTO(Slot slot) {
        return SlotDTO.builder()
                .id(slot.getSlotId())
                .facilityId(slot.getFacility().getFacilityId())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .isBooked(slot.getIsBooked())
                .build();
    }

    /**
     * Constructs a Slot entity from SlotDTO (used internally if needed).
     */
    public static Slot mapToSlotEntity(SlotDTO dto) {
        Slot slot = new Slot();
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        slot.setIsBooked(dto.getIsBooked());
        return slot;
    }

    /**
     * Creates a new Slot entity using validated input and associated Facility.
     */
    public static Slot createSlotEntity(SlotCreateRequest request, Facility facility) {
        return Slot.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isBooked(false)
                .facility(facility)
                .build();
    }

    /**
     * Prepares a client-safe SlotResponse from the Slot entity.
     */
    public static SlotResponse buildSlotResponse(Slot slot) {
        return SlotResponse.builder()
                .id(slot.getSlotId())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .booked(slot.getIsBooked())
                .build();
    }
}
