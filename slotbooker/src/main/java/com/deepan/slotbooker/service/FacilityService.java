package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.facility.FacilityRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;

import java.util.List;

public interface FacilityService {
    FacilityResponse saveFacility(FacilityRequest request);

    FacilityResponse getFacilityById(Long facilityId);

    List<FacilityResponse> getAllFacilityByVenue(Long venueId);

    FacilityResponse updateFacilityById(Long facilityId, FacilityRequest request);

    void deleteFacilityById(Long facilityId);
}
