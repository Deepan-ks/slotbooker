package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.booking.BookingRequest;
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
import com.deepan.slotbooker.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public BookingResponse bookSlot(BookingRequest request) {
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        if (Boolean.TRUE.equals(slot.getIsBooked())) {
            throw new IllegalStateException("Slot already booked");
        }

        User user = userRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Booking booking = BookingMapper.createBookingEntity(request, slot, user);

        slot.setIsBooked(true); // Update slot status
        slotRepository.save(slot);
        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.buildBookingResponse(savedBooking);
    }

    @Override
    public List<BookingResponse> getBookingsForPlayerId(Long playerId) {
        User player = userRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        List<Booking> bookings = bookingRepository.findByPlayer(player);
        return bookings.stream().map(BookingMapper::buildBookingResponse).toList();
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Booking not found"));
        return BookingMapper.buildBookingResponse(booking);
    }

    @Override
    @Transactional
    public Boolean deleteBookingForId(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus().name())) {
            return Boolean.FALSE;
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Slot slot = booking.getSlot();
        slot.setIsBooked(false);

        slotRepository.save(slot);
        bookingRepository.save(booking);

        return Boolean.TRUE;
    }

}
