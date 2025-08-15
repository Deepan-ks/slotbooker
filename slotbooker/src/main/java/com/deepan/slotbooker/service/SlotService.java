package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.slot.SlotRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;

import java.util.List;

public interface SlotService {

    /**
     * Create a new slot
     * @param facilityId
     * @param request
     * @return
     */
    SlotResponse createSlot(Long facilityId, SlotRequest request);

    /**
     * Get slot by facility
     * @param id
     * @param start
     * @param end
     * @return
     */
    List<SlotResponse> getSlotByFacility(Long id, String start, String end);

}
