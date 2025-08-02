package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.slot.SlotCreateRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;
import com.deepan.slotbooker.service.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities/{facilityId}/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    /**
     * Create a new slot
     * @param facilityId
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<SlotResponse> createSlot (@PathVariable Long facilityId, @Valid @RequestBody SlotCreateRequest request) {
        SlotResponse response = slotService.createSlot(facilityId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get slot by facility
     * @param facilityId
     * @param start
     * @param end
     * @return
     */
    @GetMapping
    public ResponseEntity<List<SlotResponse>> fetchSlotsByFacility(@PathVariable Long facilityId,
                                                                   @RequestParam(required = false) String start,
                                                                   @RequestParam(required = false) String end) {

        List<SlotResponse> responses = slotService.getSlotByFacility(facilityId, start, end);
        return ResponseEntity.ok(responses);
    }

}
