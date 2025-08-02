package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.booking.BookingCreateRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.model.Booking;

import java.util.List;

public interface BookingService {
    /**
     * Create a new booking.
     */
    BookingResponse bookSlot(BookingCreateRequest request);

    /**
     * Get all bookings.
     */
    List<BookingResponse> getBookingsForPlayerId(Long playerId);

    /**
     * Get booking by ID.
     */
    BookingResponse getBookingById(Long id);

    /**
     * Delete booking by ID.
     */
    Boolean deleteBookingForId(Long bookingId);
}
