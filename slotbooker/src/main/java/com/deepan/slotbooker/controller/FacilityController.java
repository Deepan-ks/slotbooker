package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.FacilityDTO;
import com.deepan.slotbooker.dto.facility.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.mapper.FacilityMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues/{venueId}/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityRepository facilityRepository;
    private final VenueRepository venueRepository;
    private final SportRepository sportRepository;

    @GetMapping
    public ResponseEntity<List<FacilityResponse>> getFacilitiesByVenue(@PathVariable Long venueId){
        Venue venue = venueRepository.findById(venueId).orElseThrow( () -> new IllegalArgumentException("No venue found"));
        List<Facility> facilities = facilityRepository.findByVenue(venue);
        List<FacilityResponse> responses = facilities.stream()
                .map(FacilityMapper::buildFacilityResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<FacilityResponse> addFacility(@PathVariable Long venueId, @RequestBody FacilityCreateRequest request) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new IllegalArgumentException("Venue not found"));
        Sport sport = sportRepository.findById(request.getSportId()).orElseThrow(() -> new IllegalArgumentException("Sport not found"));
        Facility facility = FacilityMapper.createFacilityEntity(request, venue, sport);
        Facility newFacility = facilityRepository.save(facility);
        FacilityResponse response = FacilityMapper.buildFacilityResponse(newFacility);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
