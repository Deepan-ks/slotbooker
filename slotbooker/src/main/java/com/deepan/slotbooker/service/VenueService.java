package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.venue.VenueRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;

import java.util.List;

public interface VenueService {
    /**
     * fetch all existing venues
     * @return
     */
    List<VenueResponse> fetchAllVenues();

    /**
     * create a new venue
     * @param request
     * @return
     */
    VenueResponse createVenue(VenueRequest request);

    VenueResponse getVenue(Long venueId);

    VenueResponse updateVenue(Long venueId, VenueRequest updateRequest);

    void deleteVenue(Long id);
}
