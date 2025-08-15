package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.slot.SlotRequest;
import com.deepan.slotbooker.dto.slot.SlotResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.SlotMapper;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.repository.FacilityRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import com.deepan.slotbooker.service.impl.SlotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SlotServiceTest {

    @Mock
    private  FacilityRepository facilityRepository;

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotServiceImpl slotService;

    @Test
    void testCreateSlot_Success() {
        // Arrange
        Long facilityId = 1L;

        SlotRequest request = new SlotRequest();
        request.setStartTime(LocalDateTime.of(2025,8,25,5,30));
        request.setEndTime(LocalDateTime.of(2025,8,25,6,30));

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);
        facility.setFacilityName("Badminton Court");

        Slot slot = new Slot();
        slot.setSlotId(100L);
        slot.setFacility(facility);
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());

        // Mock calls
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));

        try (MockedStatic<SlotMapper> mockedStatic = mockStatic(SlotMapper.class)) {
            mockedStatic.when(() -> SlotMapper.createSlotEntity(request, facility))
                    .thenReturn(slot);
            mockedStatic.when(() -> SlotMapper.buildSlotResponse(slot))
                    .thenReturn(new SlotResponse(100L, request.getStartTime(), request.getEndTime(), true));

            when(slotRepository.save(slot)).thenReturn(slot);

            // Act
            SlotResponse response = slotService.createSlot(facilityId, request);

            // Assert
            assertNotNull(response);
            assertEquals(100L, response.getId());
            assertEquals(request.getStartTime(), response.getStartTime());
            assertEquals(request.getEndTime(), response.getEndTime());
        }
    }

    @Test
    void testCreateSlot_FacilityNotFound() {
        // Arrange
        Long facilityId = 1L;
        SlotRequest request = new SlotRequest();

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> slotService.createSlot(facilityId, request));
    }

    @Test
    void testGetSlotByFacility_WithStartAndEnd_Success() {
        // Arrange
        Long facilityId = 1L;
        String start = "2025-08-09T10:00:00";
        String end = "2025-08-09T12:00:00";

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        Slot slot1 = new Slot();
        slot1.setSlotId(1L);

        Slot slot2 = new Slot();
        slot2.setSlotId(2L);

        List<Slot> slots = List.of(slot1, slot2);

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(slotRepository.findByFacilityAndStartTimeBetween(eq(facility), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(slots);

        try (MockedStatic<SlotMapper> mockedStatic = mockStatic(SlotMapper.class)) {
            mockedStatic.when(() -> SlotMapper.buildSlotResponse(any(Slot.class)))
                    .thenAnswer(invocation -> {
                        Slot s = invocation.getArgument(0);
                        return new SlotResponse(s.getSlotId(), null, null, false);
                    });

            // Act
            List<SlotResponse> responses = slotService.getSlotByFacility(facilityId, start, end);

            // Assert
            assertEquals(2, responses.size());
            assertEquals(1L, responses.get(0).getId());
            assertEquals(2L, responses.get(1).getId());
        }
    }

    @Test
    void testGetSlotByFacility_WithoutStartAndEnd_Success() {
        // Arrange
        Long facilityId = 1L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        Slot slot = new Slot();
        slot.setSlotId(2L);

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(slotRepository.findByFacility(facility)).thenReturn(List.of(slot));

        try (MockedStatic<SlotMapper> mockedStatic = mockStatic(SlotMapper.class)) {
            mockedStatic.when(() -> SlotMapper.buildSlotResponse(slot))
                    .thenReturn(new SlotResponse(2L, null, null, false));

            // Act
            List<SlotResponse> responses = slotService.getSlotByFacility(facilityId, null, null);

            // Assert
            assertEquals(1, responses.size());
            assertEquals(2L, responses.get(0).getId());
        }
    }

    @Test
    void testGetSlotByFacility_FacilityNotFound() {
        // Arrange
        Long facilityId = 1L;
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> slotService.getSlotByFacility(facilityId, null, null));
    }

}
