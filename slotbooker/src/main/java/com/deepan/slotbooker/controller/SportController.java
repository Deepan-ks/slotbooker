package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.sportDTO.SportCreateRequest;
import com.deepan.slotbooker.dto.sportDTO.SportResponse;
import com.deepan.slotbooker.dto.sportDTO.SportUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.service.SportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Sport-related API endpoints.
 * All endpoints are now prefixed with "/api/v1".
 */
@RestController
@RequestMapping("/api/v1/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    /**
     * Adds a new sport.
     *
     * @param request The DTO containing sport creation details.
     * @return A ResponseEntity with the newly created sport and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<SportResponse> addSport(@Valid @RequestBody SportCreateRequest request) {
        try {
            SportResponse response = sportService.addSport(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all sports.
     *
     * @return A ResponseEntity with a list of all sports and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<SportResponse>> getAllSports() {
        List<SportResponse> sports = sportService.fetchAllSports();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    /**
     * Updates an existing sport.
     *
     * @param sportId The ID of the sport to update.
     * @param request The DTO with fields to update.
     * @return A ResponseEntity with the updated sport and HTTP status 200 (OK), or 404 (Not Found).
     */
    @PutMapping("/{sportId}")
    public ResponseEntity<SportResponse> updateSport(@PathVariable Long sportId, @Valid @RequestBody SportUpdateRequest request) {
        try {
            SportResponse response = sportService.updateSport(sportId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a sport.
     *
     * @param sportId The ID of the sport to delete.
     * @return A ResponseEntity with HTTP status 204 (No Content) on success, or 404 (Not Found).
     */
    @DeleteMapping("/{sportId}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long sportId) {
        Boolean isDeleted = sportService.deleteSport(sportId);
        if (Boolean.TRUE.equals(isDeleted)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
