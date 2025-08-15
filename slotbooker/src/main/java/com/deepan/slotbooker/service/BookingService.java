package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.booking.BookingRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;

import java.util.List;

public interface BookingService {
    /**
     * Create a new booking.
     */
    BookingResponse bookSlot(BookingRequest request);

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
