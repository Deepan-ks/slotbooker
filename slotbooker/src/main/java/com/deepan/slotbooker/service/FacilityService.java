package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.facilityDTO.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facilityDTO.FacilityResponse;
import com.deepan.slotbooker.dto.facilityDTO.FacilityUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for Facility-related business logic.
 */
public interface FacilityService {
    /**
     * Adds a new facility to a venue.
     *
     * @param venueId The ID of the venue to which the facility belongs.
     * @param request DTO containing facility details.
     * @return FacilityResponse DTO of the newly created facility.
     */
    @Transactional
    FacilityResponse addFacility(Long venueId, FacilityCreateRequest request);

    /**
     * Retrieves all facilities for a given venue.
     *
     * @param venueId The ID of the venue.
     * @return A list of FacilityResponse DTOs.
     */
    List<FacilityResponse> getFacilitiesByVenue(Long venueId);

    /**
     * Updates an existing facility.
     *
     * @param facilityId The ID of the facility to update.
     * @param request DTO containing the updated facility details.
     * @return FacilityResponse DTO of the updated facility.
     */
    @Transactional
    FacilityResponse updateFacility(Long facilityId, FacilityUpdateRequest request);

    /**
     * Deletes a facility.
     *
     * @param facilityId The ID of the facility to delete.
     * @return true if the facility was successfully deleted, false otherwise.
     */
    @Transactional
    Boolean deleteFacility(Long facilityId);
}
