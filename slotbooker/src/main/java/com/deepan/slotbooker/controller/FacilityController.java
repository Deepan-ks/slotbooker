package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.facilityDTO.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facilityDTO.FacilityResponse;
import com.deepan.slotbooker.dto.facilityDTO.FacilityUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.service.FacilityService;
import com.deepan.slotbooker.service.impl.FacilityServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Facility-related API endpoints.
 * All endpoints are now prefixed with "/api/v1".
 */
@RestController
@RequestMapping("/api/v1/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    /**
     * Adds a new facility to a venue.
     *
     * @param venueId The ID of the venue to which the facility belongs.
     * @param request The DTO with facility creation details.
     * @return A ResponseEntity with the created facility and HTTP status 201 (Created).
     */
    @PostMapping("/{venueId}")
    public ResponseEntity<FacilityResponse> addFacility(@PathVariable Long venueId, @Valid @RequestBody FacilityCreateRequest request) {
        try {
            FacilityResponse response = facilityService.addFacility(venueId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all facilities for a specific venue.
     *
     * @param venueId The ID of the venue.
     * @return A ResponseEntity with a list of facilities and HTTP status 200 (OK).
     */
    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<FacilityResponse>> getFacilitiesByVenue(@PathVariable Long venueId) {
        try {
            List<FacilityResponse> facilities = facilityService.getFacilitiesByVenue(venueId);
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing facility.
     *
     * @param facilityId The ID of the facility to update.
     * @param request The DTO with fields to update.
     * @return A ResponseEntity with the updated facility and HTTP status 200 (OK), or 404 (Not Found).
     */
    @PutMapping("/{facilityId}")
    public ResponseEntity<FacilityResponse> updateFacility(@PathVariable Long facilityId, @Valid @RequestBody FacilityUpdateRequest request) {
        try {
            FacilityResponse response = facilityService.updateFacility(facilityId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a facility.
     *
     * @param facilityId The ID of the facility to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) on success, or 404 (Not Found).
     */
    @DeleteMapping("/{facilityId}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long facilityId) {
        Boolean isDeleted = facilityService.deleteFacility(facilityId);
        if (Boolean.TRUE.equals(isDeleted)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
