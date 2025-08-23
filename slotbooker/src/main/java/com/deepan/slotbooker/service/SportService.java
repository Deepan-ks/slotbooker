package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.sportDTO.SportCreateRequest;
import com.deepan.slotbooker.dto.sportDTO.SportResponse;
import com.deepan.slotbooker.dto.sportDTO.SportUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for Sport-related business logic.
 */
public interface SportService {
    /**
     * Adds a new sport.
     *
     * @param sportCreateRequest DTO containing sport details.
     * @return SportResponse DTO of the newly created sport.
     */
    @Transactional
    SportResponse addSport(SportCreateRequest sportCreateRequest);

    /**
     * Retrieves all available sports.
     *
     * @return A list of SportResponse DTOs.
     */
    List<SportResponse> fetchAllSports();

    /**
     * Updates an existing sport.
     *
     * @param sportId The ID of the sport to update.
     * @param request DTO containing the updated sport details.
     * @return SportResponse DTO of the updated sport.
     */
    @Transactional
    SportResponse updateSport(Long sportId, SportUpdateRequest request);

    /**
     * Deletes a sport.
     *
     * @param sportId The ID of the sport to delete.
     * @return true if the sport was successfully deleted, false otherwise.
     */
    @Transactional
    Boolean deleteSport(Long sportId);
}
