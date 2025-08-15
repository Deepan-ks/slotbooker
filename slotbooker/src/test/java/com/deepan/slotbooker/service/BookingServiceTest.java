package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.booking.BookingCreateRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.BookingMapper;
import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.enums.BookingStatus;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.BookingRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.impl.BookingServiceImpl;
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
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;


    @Test
    void testBookSlot_Success(){
        // Arrange
        Long id = 1L;

        //Slot
        Slot slot = new Slot();
        slot.setSlotId(id);
        slot.setIsBooked(false);

        // User
        User user = new User();
        user.setUserId(id);

        // Booking Request
        BookingCreateRequest request = new BookingCreateRequest();
        request.setSlotId(id);
        request.setPlayerId(id);

        // Booking Response
        BookingResponse response = new BookingResponse();
        response.setBookingId(id);
        response.setSlotId(id);
        response.setStatus(BookingStatus.BOOKED.name());

        // Booking
        Booking booking = new Booking();
        booking.setBookingId(id);

        when(slotRepository.findById(id)).thenReturn(Optional.of(slot));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(bookingRepository.save(booking)).thenReturn(booking);
        when(slotRepository.save(slot)).thenReturn(slot);

        try(MockedStatic<BookingMapper> mockedStatic = Mockito.mockStatic(BookingMapper.class)){
            mockedStatic.when(() -> BookingMapper.createBookingEntity(request, slot, user)).thenReturn(booking);
            mockedStatic.when(() -> BookingMapper.buildBookingResponse(booking)).thenReturn(response);

            BookingResponse result = bookingService.bookSlot(request);

            assertNotNull(result);
            assertEquals(booking.getBookingId(), result.getBookingId());
        }

    }

    @Test
    void testBookSlot_SlotAlreadyBooked(){
        // Arrange
        Long id = 1L;

        //Slot
        Slot slot = new Slot();
        slot.setSlotId(id);
        slot.setIsBooked(true);

        // Booking Request
        BookingCreateRequest request = new BookingCreateRequest();
        request.setSlotId(id);
        request.setPlayerId(id);

        when(slotRepository.findById(1L)).thenReturn(Optional.of(slot));

        assertThrows(IllegalStateException.class, () -> bookingService.bookSlot(request));
        verifyNoInteractions(bookingRepository, userRepository);
    }


    @Test
    void testGetBookingsForPlayerId_Success(){
        // Arrange
        Long id = 1L;

        // User
        User user = new User();
        user.setUserName("TestUser");
        user.setUserId(id);

        // Booking
        Booking booking = new Booking();
        booking.setBookingId(id);
        booking.setPlayer(user);

        //Response
        BookingResponse response = new BookingResponse();
        response.setBookingId(id);
        response.setPlayerName("TestUser");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(bookingRepository.findByPlayer(user)).thenReturn(List.of(booking));

        try(MockedStatic<BookingMapper> mockedStatic = Mockito.mockStatic(BookingMapper.class)){
            mockedStatic.when(() -> BookingMapper.buildBookingResponse(booking)).thenReturn(response);
            List<BookingResponse> responses = bookingService.getBookingsForPlayerId(id);

            assertNotNull(responses);
            assertEquals(1, responses.size());
            assertEquals("TestUser", responses.get(0).getPlayerName());
        }
    }

    @Test
    void testGetBookingsForPlayerId_NotFound(){
        // Arrange
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {bookingService.getBookingsForPlayerId(1L);});
    }

    @Test
    void testGetBookingById_Success() {

        //Arrange
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingId(bookingId);

        BookingResponse response = new BookingResponse();
        response.setBookingId(bookingId);

        //Act
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        try(MockedStatic<BookingMapper> mockedStatic = Mockito.mockStatic(BookingMapper.class)){
            mockedStatic.when(() -> BookingMapper.buildBookingResponse(booking)).thenReturn(response);

            BookingResponse bookingResponse = bookingService.getBookingById(bookingId);

            //Assert
            assertNotNull(bookingResponse);
            assertEquals(booking.getBookingId(), bookingResponse.getBookingId());
            mockedStatic.verify(() -> BookingMapper.buildBookingResponse(booking), times(1));

        }
    }

    @Test
    void testGetBookingById_Exception(){
        // Arrange
        Long bookingId = 1L;
        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(bookingId));
    }

    @Test
    void testDeleteBookingForId_Success(){
        // Arrange
        Long id = 1L;

        // slot
        Slot slot = new Slot();
        slot.setSlotId(id);
        slot.setIsBooked(true);

        // booking
        Booking booking = new Booking();
        booking.setBookingId(id);
        booking.setStatus(BookingStatus.BOOKED);
        booking.setSlot(slot);

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(slotRepository.save(slot)).thenReturn(slot);
        when(bookingRepository.save(booking)).thenReturn(booking);

        // Act
        Boolean result = bookingService.deleteBookingForId(id);

        // Assert
        assertTrue(result);
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        assertFalse(slot.getIsBooked());
        verify(slotRepository).save(slot);
        verify(bookingRepository).save(booking);

    }

    @Test
    void testDeleteBookingForId_AlreadyCancelled(){
        // Arrange
        Long id = 1L;

        // booking
        Booking booking = new Booking();
        booking.setBookingId(id);
        booking.setStatus(BookingStatus.CANCELLED);

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));

        // Act
        Boolean result = bookingService.deleteBookingForId(id);

        // Assert
        assertFalse(result);
        verifyNoMoreInteractions(slotRepository, bookingRepository);
    }

    @Test
    void testDeleteBookingForId_NotFound(){
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {bookingService.deleteBookingForId(1L);});
        verifyNoInteractions(slotRepository);
    }
}
