package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.facilityDTO.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facilityDTO.FacilityResponse;
import com.deepan.slotbooker.dto.facilityDTO.FacilityUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.FacilityService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public FacilityResponse addFacility(Long venueId, FacilityCreateRequest request) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));

        Facility facility = Facility.builder()
                .name(request.getName())
                .pricePerHour(request.getPricePerHour())
                .venue(venue)
                .sport(sport)
                .build();

        Facility newFacility = facilityRepository.save(facility);
        return Mapper.buildFacilityResponse(newFacility);
    }

    @Override
    public List<FacilityResponse> getFacilitiesByVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        List<Facility> facilities = facilityRepository.findByVenue(venue);
        return Mapper.buildFacilityResponseList(facilities);
    }

    @Override
    @Transactional
    public FacilityResponse updateFacility(Long facilityId, FacilityUpdateRequest request) {
        Facility existingFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        Sport existingSport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));

        if (request.getName() != null) {
            existingFacility.setName(request.getName());
        }
        if (request.getPricePerHour() != null) {
            existingFacility.setPricePerHour(request.getPricePerHour());
        }
        if(request.getSportId() != null){
            existingFacility.setSport(existingSport);
        }

        Facility updatedFacility = facilityRepository.save(existingFacility);
        return Mapper.buildFacilityResponse(updatedFacility);
    }

    @Override
    @Transactional
    public Boolean deleteFacility(Long facilityId) {
        if (facilityRepository.existsById(facilityId)) {
            facilityRepository.deleteById(facilityId);
            return true;
        }
        return false;
    }
}
