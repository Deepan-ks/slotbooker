package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.facility.FacilityRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.service.FacilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    // Create facility (OWNER only)
    @PostMapping("/facilities")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<FacilityResponse> createFacility(@Valid @RequestBody FacilityRequest request) {
        return ResponseEntity.ok(facilityService.saveFacility(request));
    }

    // Get facility by id (PLAYER or OWNER)
    @GetMapping("/facilities/{id}")
    @PreAuthorize("hasAnyRole('PLAYER','OWNER')")
    public ResponseEntity<FacilityResponse> getFacility(@PathVariable Long id) {
        return ResponseEntity.ok(facilityService.getFacilityById(id));
    }

    // List facilities by venue (PLAYER or OWNER)
    @GetMapping("/venues/{venueId}/facilities")
    @PreAuthorize("hasAnyRole('PLAYER','OWNER')")
    public ResponseEntity<List<FacilityResponse>> getAllFacility(@PathVariable Long venueId) {
        return ResponseEntity.ok(facilityService.getAllFacilityByVenue(venueId));
    }

    // Update facility (OWNER only)
    @PutMapping("/facilities/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<FacilityResponse> updateFacility(@PathVariable Long id, @Valid @RequestBody FacilityRequest request) {
        return ResponseEntity.ok(facilityService.updateFacilityById(id, request));
    }

    // Delete facility (OWNER only)
    @DeleteMapping("/facilities/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacilityById(id);
        return ResponseEntity.noContent().build();
    }

}
