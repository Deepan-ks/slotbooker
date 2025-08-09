package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.sport.SportResponse;
import com.deepan.slotbooker.mapper.SportsMapper;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.service.impl.SportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SportServiceTest {

    @Mock
    private SportRepository sportRepository;

    @InjectMocks
    private SportServiceImpl service;

    @Test
    void testFetchAllSport_Success(){
        //Arrange
        Sport sport1 = new Sport();
        sport1.setSportId(1L);
        sport1.setSportName("Football");

        Sport sport2 = new Sport();
        sport2.setSportId(2L);
        sport2.setSportName("Cricket");

        List<Sport> sportsList = Arrays.asList(sport1, sport2);
        when(sportRepository.findAll()).thenReturn(sportsList);

        try(MockedStatic<SportsMapper> mockedStatic = Mockito.mockStatic(SportsMapper.class)){
            mockedStatic.when(() -> SportsMapper.buildSportResponse(sport1))
                    .thenReturn(new SportResponse(1L, "Football"));
            mockedStatic.when(() -> SportsMapper.buildSportResponse(sport2))
                    .thenReturn(new SportResponse(2L, "Cricket"));

            //Act
            List<SportResponse> sportResponseList = service.fetchAllSports();

            //Assert
            assertNotNull(sportResponseList);
            assertEquals(sport1.getSportName(), sportResponseList.get(0).getName());
            assertEquals(sport2.getSportName(), sportResponseList.get(1).getName());

            mockedStatic.verify(() -> SportsMapper.buildSportResponse(sport1));
            mockedStatic.verify(() -> SportsMapper.buildSportResponse(sport2));
            verify(sportRepository, times(1)).findAll();
        }
    }

    @Test
    void testFetchAllSport_EmptyList(){
        when(sportRepository.findAll()).thenReturn(Collections.emptyList());

        List<SportResponse> responses = service.fetchAllSports();

        assertTrue(responses.isEmpty());
        verify(sportRepository).findAll();
    }

}
