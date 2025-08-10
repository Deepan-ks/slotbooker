package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.venue.VenueCreateRequest;
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
    VenueResponse createVenue(VenueCreateRequest request);
}
