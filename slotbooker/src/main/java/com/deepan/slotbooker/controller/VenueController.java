package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.venueDTO.VenueCreateRequest;
import com.deepan.slotbooker.dto.venueDTO.VenueResponse;
import com.deepan.slotbooker.dto.venueDTO.VenueUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Venue-related API endpoints.
 * All endpoints are now prefixed with "/api/v1".
 */
@RestController
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    /**
     * Creates a new venue for a given owner.
     *
     * @param ownerId The ID of the owner.
     * @param request The DTO containing venue details.
     * @return A ResponseEntity with the created venue and HTTP status 201 (Created).
     */
    @PostMapping("/{ownerId}")
    public ResponseEntity<VenueResponse> createVenue(@PathVariable Long ownerId, @Valid @RequestBody VenueCreateRequest request) {
        try {
            VenueResponse response = venueService.createVenue(request, ownerId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all venues.
     *
     * @return A ResponseEntity with a list of all venues and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        List<VenueResponse> venues = venueService.fetchAllVenues();
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    /**
     * Retrieves all venues by a specific owner.
     *
     * @param ownerId The ID of the owner.
     * @return A ResponseEntity with a list of venues and HTTP status 200 (OK).
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<VenueResponse>> getVenuesByOwner(@PathVariable Long ownerId) {
        List<VenueResponse> venues = venueService.fetchVenuesByOwner(ownerId);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    /**
     * Retrieves all venues by city.
     *
     * @param city The city name.
     * @return A ResponseEntity with a list of venues and HTTP status 200 (OK).
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<VenueResponse>> getVenuesByCity(@PathVariable String city) {
        List<VenueResponse> venues = venueService.fetchVenuesByCity(city);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    /**
     * Updates an existing venue.
     *
     * @param venueId The ID of the venue to update.
     * @param request The DTO with fields to update.
     * @return A ResponseEntity with the updated venue and HTTP status 200 (OK), or 404 (Not Found).
     */
    @PutMapping("/{venueId}")
    public ResponseEntity<VenueResponse> updateVenue(@PathVariable Long venueId, @Valid @RequestBody VenueUpdateRequest request) {
        try {
            VenueResponse response = venueService.updateVenue(venueId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a venue.
     *
     * @param venueId The ID of the venue to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) on success, or 404 (Not Found).
     */
    @DeleteMapping("/{venueId}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long venueId) {
        Boolean isDeleted = venueService.deleteVenue(venueId);
        if (Boolean.TRUE.equals(isDeleted)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
