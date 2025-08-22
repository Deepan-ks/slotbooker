package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.facility.FacilityRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.impl.FacilityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacilityServiceTest {

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private SportRepository sportRepository;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    private FacilityRequest facilityRequest;
    private Venue venue;
    private Sport sport;
    private Facility facility;

    @BeforeEach
    void prepareTest(){
        facilityRequest = FacilityRequest.builder()
                .facilityName("Court A")
                .venueId(10L)
                .sportId(2L)
                .pricePerHour(300.0)
                .build();
        venue = Venue.builder().venueId(10L).build();
        sport = Sport.builder().sportId(2L).sportName("Badminton").build();
        facility = Facility.builder()
                .facilityId(1L)
                .facilityName("Court A")
                .venue(venue)
                .sport(sport)
                .pricePerHour(300.0)
                .build();
    }
    @Test
    void testCreateFacility_Success(){
        when(venueRepository.findById(10L)).thenReturn(Optional.of(venue));
        when(sportRepository.findById(2L)).thenReturn(Optional.of(sport));
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        FacilityResponse response = facilityService.saveFacility(facilityRequest);

        assertNotNull(response);
        assertEquals(facilityRequest.getFacilityName(), response.getFacilityName());

    }
}
