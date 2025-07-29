package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.booking.BookingCreateRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.model.Booking;

import java.util.List;

public interface BookingService {
    BookingResponse bookSlot(BookingCreateRequest request);
    List<BookingResponse> getBookingsForPlayerId(Long playerId);
    Boolean deleteBookingForId(Long bookingId);
}
