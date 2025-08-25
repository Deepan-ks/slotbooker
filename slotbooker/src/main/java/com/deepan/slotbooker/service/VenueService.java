package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.venueDTO.VenueCreateRequest;
import com.deepan.slotbooker.dto.venueDTO.VenueResponse;
import com.deepan.slotbooker.dto.venueDTO.VenueUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for Venue-related business logic.
 */
public interface VenueService {
    /**
     * Creates a new venue.
     *
     * @param request The DTO containing venue creation details.
     * @param ownerId The ID of the venue owner.
     * @return VenueResponse DTO of the newly created venue.
     */
    @Transactional
    VenueResponse createVenue(VenueCreateRequest request, Long ownerId);

    /**
     * Fetches all venues.
     *
     * @return A list of VenueResponse DTOs.
     */
    List<VenueResponse> fetchAllVenues();

    /**
     * Fetches all venues owned by a specific user.
     *
     * @param ownerId The ID of the owner.
     * @return A list of VenueResponse DTOs.
     */
    List<VenueResponse> fetchVenuesByOwner(Long ownerId);

    /**
     * Fetches all venues by city.
     *
     * @param city The city name.
     * @return A list of VenueResponse DTOs.
     */
    List<VenueResponse> fetchVenuesByCity(String city);

    /**
     * Updates an existing venue.
     *
     * @param venueId The ID of the venue to update.
     * @param request DTO containing the updated venue details.
     * @return VenueResponse DTO of the updated venue.
     */
    @Transactional
    VenueResponse updateVenue(Long venueId, VenueUpdateRequest request);

    /**
     * Deletes a venue.
     *
     * @param venueId The ID of the venue to delete.
     * @return true if the venue was successfully deleted, false otherwise.
     */
    @Transactional
    Boolean deleteVenue(Long venueId);
}
