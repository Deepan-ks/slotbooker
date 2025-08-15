package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.slot.SlotRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;

public class SlotMapper {

    /**
     * Creates a new Slot entity using validated input and associated Facility.
     */
    public static Slot createSlotEntity(SlotRequest request, Facility facility) {
        return Slot.builder()
                .facility(facility)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isBooked(false)
                .build();
    }

    /**
     * Prepares a client-safe SlotResponse from the Slot entity.
     */
    public static SlotResponse buildSlotResponse(Slot slot) {
        return SlotResponse.builder()
                .slotId(slot.getSlotId())
                .facilityId(slot.getFacility().getFacilityId())
                .facilityName(slot.getFacility().getFacilityName())
                .sportName(slot.getFacility().getSport().getSportName())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .isBooked(slot.getIsBooked())
                .build();
    }
}
