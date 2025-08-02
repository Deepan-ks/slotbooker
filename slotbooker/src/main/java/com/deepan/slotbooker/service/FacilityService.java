package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.facility.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;

import java.util.List;

public interface FacilityService {
    /**
     * Get facilities by venue
     * @param venueId
     * @return
     */
    List<FacilityResponse> getFacilitiesByVenue(Long venueId);

    /**
     * Adds a new facility to venue
     * @param venueId
     * @param request
     * @return
     */
    FacilityResponse addFacility(Long venueId, FacilityCreateRequest request);
}
