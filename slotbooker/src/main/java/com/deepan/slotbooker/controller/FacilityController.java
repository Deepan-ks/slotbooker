package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.facility.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues/{venueId}/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    /**
     * Get facilities of venue
     * @param venueId
     * @return
     */
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    @GetMapping
    public ResponseEntity<List<FacilityResponse>> getFacilitiesByVenue(@PathVariable Long venueId){
        List<FacilityResponse> responses = facilityService.getFacilitiesByVenue(venueId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Add a new facility for venue
     * @param venueId
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<FacilityResponse> addFacility(@PathVariable Long venueId, @RequestBody FacilityCreateRequest request) {
        FacilityResponse response = facilityService.addFacility(venueId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
