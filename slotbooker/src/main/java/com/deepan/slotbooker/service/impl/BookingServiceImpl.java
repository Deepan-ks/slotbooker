package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.bookingDTO.BookingCreateRequest;
import com.deepan.slotbooker.dto.bookingDTO.BookingResponse;
import com.deepan.slotbooker.dto.bookingDTO.BookingUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.enums.BookingStatus;
import com.deepan.slotbooker.model.enums.Roles;
import com.deepan.slotbooker.repository.BookingRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public BookingResponse bookSlot(Long playerId, BookingCreateRequest request) {
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));
        User player = userRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        // Business logic: check if the slot is already booked
        if (Boolean.TRUE.equals(slot.getIsBooked())) {
            throw new IllegalStateException("Slot is already booked.");
        }

        // Business logic: only players can book slots
        if (!Roles.PLAYER.equals(player.getRole())) {
            throw new IllegalArgumentException("Only players can book slots.");
        }

        Booking booking = Booking.builder()
                .slot(slot)
                .player(player)
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.CONFIRMED)
                .build();

        slot.setIsBooked(true);
        slotRepository.save(slot);
        Booking savedBooking = bookingRepository.save(booking);

        return Mapper.buildBookingResponse(savedBooking);
    }

    @Override
    @Transactional
    public Boolean cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Business logic: don't allow cancelling already cancelled bookings
        if (BookingStatus.CANCELLED.equals(booking.getStatus())) {
            return false;
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        // Also free up the booked slot
        Slot slot = booking.getSlot();
        slot.setIsBooked(false);
        slotRepository.save(slot);

        return true;
    }

    @Override
    @Transactional
    public BookingResponse getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return Mapper.buildBookingResponse(booking);
    }

    @Override
    @Transactional
    public List<BookingResponse> getBookingsForPlayer(Long playerId) {
        User player = userRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        List<Booking> bookings = bookingRepository.findByPlayer(player);
        return Mapper.buildBookingResponseList(bookings);
    }

    @Override
    @Transactional
    public BookingResponse updateBooking(Long bookingId, BookingUpdateRequest request) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (request.getStatus() != null) {
            existingBooking.setStatus(request.getStatus());
        }

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return Mapper.buildBookingResponse(updatedBooking);
    }
}
