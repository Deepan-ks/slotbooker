package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.venue.VenueCreateRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.VenueMapper;
import com.deepan.slotbooker.model.Role;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.impl.VenueServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VenueServiceImpl venueService;

    @Test
    void testCreateVenue_Success(){
        //Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUserRole(Role.OWNER);

        VenueCreateRequest request = new VenueCreateRequest();
        request.setName("Arena X");
        request.setLocation("Coimbatore");
        request.setOwnerId(1L);

        Venue venue = new Venue();
        venue.setVenueId(1L);

        VenueResponse response = new VenueResponse(1L, "Arena X", "Coimbatore", 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(venueRepository.save(venue)).thenReturn(venue);

        try(MockedStatic<VenueMapper> mockedStatic = Mockito.mockStatic(VenueMapper.class)){
            mockedStatic.when(() -> VenueMapper.createVenueEntity(request, user)).thenReturn(venue);
            mockedStatic.when(() -> VenueMapper.buildVenueResponse(venue)).thenReturn(response);

            //Act
            VenueResponse result = venueService.createVenue(request);

            //Assert
            assertNotNull(result);
            assertEquals(venue.getVenueId(), result.getId());
            verify(venueRepository).save(venue);
        }

    }

    @Test
    void testCreateVenue_UserIsNotOwner(){
        //Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUserRole(Role.PLAYER);

        VenueCreateRequest request = new VenueCreateRequest();
        request.setName("Arena X");
        request.setLocation("Coimbatore");
        request.setOwnerId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //Act & Assert
        assertThrows(IllegalStateException.class, () -> venueService.createVenue(request));
        verifyNoInteractions(venueRepository);
    }

    @Test
    void testCreateVenue_UserNotFound(){
        //Arrange
        Long id =1L;

        VenueCreateRequest request = new VenueCreateRequest();
        request.setName("Arena X");
        request.setLocation("Coimbatore");
        request.setOwnerId(1L);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> venueService.createVenue(request));
        verifyNoInteractions(venueRepository);
    }

    @Test
    void testFetchAllVenues_Success(){
        // Arrange
        Venue venue1 = new Venue();
        venue1.setVenueId(1L);

        Venue venue2 = new Venue();
        venue2.setVenueId(2L);

        List<Venue> venues = List.of(venue1, venue2);

        when(venueRepository.findAll()).thenReturn(venues);

        try(MockedStatic<VenueMapper> mockedStatic = Mockito.mockStatic(VenueMapper.class)){
            mockedStatic.when(() -> VenueMapper.buildVenueResponse(venue1))
                    .thenReturn(new VenueResponse(1L, "Arena X", "Coimbatore", 1L));
            mockedStatic.when(() -> VenueMapper.buildVenueResponse(venue2))
                    .thenReturn(new VenueResponse(2L, "Arena Z", "Chennai", 2L));

            List<VenueResponse> responses = venueService.fetchAllVenues();

            assertNotNull(responses);
            assertEquals(venues.size(), responses.size());
            assertEquals(venue1.getVenueId(), responses.get(0).getId());
            assertEquals(venue2.getVenueId(), responses.get(1).getId());

            mockedStatic.verify(() -> VenueMapper.buildVenueResponse(venue1), times(1));
            mockedStatic.verify(() -> VenueMapper.buildVenueResponse(venue2), times(1));
        }

    }

}
