package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.facility.FacilityRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.FacilityMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.FacilityService;
import jakarta.transaction.Transactional;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final VenueRepository venueRepository;
    private final SportRepository sportRepository;

    @Override
    public FacilityResponse saveFacility(FacilityRequest request) {
        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));

        Facility facility = FacilityMapper.createFacilityEntity(request, venue, sport);
        Facility saved = facilityRepository.save(facility);
        return FacilityMapper.buildFacilityResponse(saved);
    }

    @Override
    public FacilityResponse getFacilityById(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        return FacilityMapper.buildFacilityResponse(facility);
    }

    @Override
    public List<FacilityResponse> getAllFacilityByVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        List<Facility> facilities = facilityRepository.findByVenue(venue);
        return FacilityMapper.facilityResponseList(facilities);
    }

    @Override
    public FacilityResponse updateFacilityById(Long facilityId, FacilityRequest request) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));

        FacilityMapper.updateEntity(facility, request, venue, sport);
        return FacilityMapper.buildFacilityResponse(facilityRepository.save(facility));
    }

    @Override
    public void deleteFacilityById(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        facilityRepository.delete(facility);
    }
}
