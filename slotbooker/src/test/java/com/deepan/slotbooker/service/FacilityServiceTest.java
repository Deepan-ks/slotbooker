package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.facility.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.mapper.FacilityMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.impl.FacilityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testAddFacility_Success() {
        //Arrange
        long venueId = 1L;
        Long sportId = 10L;

        FacilityCreateRequest request = new FacilityCreateRequest();
        request.setName("Badminton Court");
        request.setSportId(sportId);

        Venue venue = new Venue();
        venue.setVenueId(venueId);
        venue.setVenueName("Test Venue");

        Sport sport = new Sport();
        sport.setSportId(sportId);
        sport.setSportName("Badminton");

        Facility facility = new Facility();
        facility.setFacilityId(100L);
        facility.setFacilityName("Badminton Court");
        facility.setVenue(venue);
        facility.setSport(sport);

        // Mock repository calls
        when(venueRepository.findById(venueId)).thenReturn(Optional.of(venue));
        when(sportRepository.findById(sportId)).thenReturn(Optional.of(sport));

        try (MockedStatic<FacilityMapper> mockedStatic = mockStatic(FacilityMapper.class)) {
            mockedStatic.when(() -> FacilityMapper.createFacilityEntity(request, venue, sport)).thenReturn(facility);
            mockedStatic.when(() -> FacilityMapper.buildFacilityResponse(facility))
                    .thenReturn(new FacilityResponse(100L, "Badminton Court", 120.0, venue.getVenueId(), sport.getSportName()));

            when(facilityRepository.save(facility)).thenReturn(facility);

            // Act
            FacilityResponse response = facilityService.addFacility(venueId, request);

            // Assert
            assertNotNull(response);
            assertEquals(100L, response.getId());
            assertEquals("Badminton Court", response.getName());
        }
    }

    @Test
    void testAddFacility_VenueNotFound() {
        // Arrange
        Long venueId = 1L;
        FacilityCreateRequest request = new FacilityCreateRequest();
        request.setSportId(10L);

        when(venueRepository.findById(venueId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> facilityService.addFacility(venueId, request));
    }

    @Test
    void testAddFacility_SportNotFound() {
        // Arrange
        Long venueId = 1L;
        Long sportId = 10L;

        FacilityCreateRequest request = new FacilityCreateRequest();
        request.setSportId(sportId);

        Venue venue = new Venue();
        venue.setVenueId(venueId);

        when(venueRepository.findById(venueId)).thenReturn(Optional.of(venue));
        when(sportRepository.findById(sportId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> facilityService.addFacility(venueId, request));
    }

    @Test
    void testGetFacilitiesByVenue_Success(){
        //Arrange
        long venueId = 1L;

        Venue venue = new Venue();
        venue.setVenueId(venueId);
        venue.setVenueName("Test Venue 1");

        Sport sport = new Sport();
        sport.setSportId(1L);
        sport.setSportName("Badminton");

        Facility facility1 = new Facility();
        facility1.setFacilityId(1L);
        facility1.setFacilityName("Badminton Court A");
        facility1.setVenue(venue);
        facility1.setSport(sport);

        Facility facility2 = new Facility();
        facility2.setFacilityId(2L);
        facility2.setFacilityName("Badminton Court B");
        facility2.setVenue(venue);
        facility2.setSport(sport);

        List<Facility> facilities = List.of(facility1, facility2);

        // Mock repository calls
        when(venueRepository.findById(venueId)).thenReturn(Optional.of(venue));
        when(facilityRepository.findByVenue(venue)).thenReturn(facilities);

        try(MockedStatic<FacilityMapper> mockedStatic = Mockito.mockStatic(FacilityMapper.class)){
            mockedStatic.when(() -> FacilityMapper.buildFacilityResponse(facility1))
                    .thenReturn(new FacilityResponse(1L, "Badminton Court A", 120.0, venue.getVenueId(), sport.getSportName()));
            mockedStatic.when(() -> FacilityMapper.buildFacilityResponse(facility2))
                    .thenReturn(new FacilityResponse(2L, "Badminton Court B", 120.0, venue.getVenueId(), sport.getSportName()));

            List<FacilityResponse> responses = facilityService.getFacilitiesByVenue(venueId);

            assertNotNull(responses);
            assertEquals(facilities.size(), responses.size());
            assertEquals(facility1.getFacilityName(), responses.get(0).getName());
            assertEquals(facility2.getFacilityName(), responses.get(1).getName());
            mockedStatic.verify(() -> FacilityMapper.buildFacilityResponse(facility1));
            mockedStatic.verify(() -> FacilityMapper.buildFacilityResponse(facility2));

        }
    }

    @Test
    void testGetFacilitiesByVenue_VenueNotFound() {
        // Arrange
        Long venueId = 1L;
        when(venueRepository.findById(venueId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> facilityService.getFacilitiesByVenue(venueId));
    }
}
