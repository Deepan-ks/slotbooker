package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.facility.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.mapper.FacilityMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.FacilityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private SportRepository sportRepository;

    @Override
    public List<FacilityResponse> getFacilitiesByVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElseThrow( () -> new IllegalArgumentException("No venue found"));
        List<Facility> facilities = facilityRepository.findByVenue(venue);
        return facilities.stream().map(FacilityMapper::buildFacilityResponse).toList();
    }

    @Override
    @Transactional
    public FacilityResponse addFacility(Long venueId, FacilityCreateRequest request) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new IllegalArgumentException("Venue not found"));
        Sport sport = sportRepository.findById(request.getSportId()).orElseThrow(() -> new IllegalArgumentException("Sport not found"));
        Facility facility = FacilityMapper.createFacilityEntity(request, venue, sport);
        Facility newFacility = facilityRepository.save(facility);
        return FacilityMapper.buildFacilityResponse(newFacility);
    }
}
