package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.bookingDTO.BookingCreateRequest;
import com.deepan.slotbooker.dto.bookingDTO.BookingResponse;
import com.deepan.slotbooker.dto.bookingDTO.BookingUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for Booking-related business logic.
 */
public interface BookingService {
    /**
     * Books an available time slot for a player.
     *
     * @param playerId The ID of the player making the booking.
     * @param request DTO containing the slot ID.
     * @return BookingResponse DTO of the new booking.
     */
    @Transactional
    BookingResponse bookSlot(Long playerId, BookingCreateRequest request);

    /**
     * Cancels an existing booking.
     *
     * @param bookingId The ID of the booking to cancel.
     * @return True if the booking was successfully cancelled, false otherwise.
     */
    @Transactional
    Boolean cancelBooking(Long bookingId);

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId The ID of the booking.
     * @return BookingResponse DTO.
     */
    BookingResponse getBookingById(Long bookingId);

    /**
     * Retrieves all bookings for a specific player.
     *
     * @param playerId The ID of the player.
     * @return A list of BookingResponse DTOs.
     */
    List<BookingResponse> getBookingsForPlayer(Long playerId);

    /**
     * Updates an existing booking.
     *
     * @param bookingId The ID of the booking to update.
     * @param request DTO containing the updated booking details.
     * @return BookingResponse DTO of the updated booking.
     */
    @Transactional
    BookingResponse updateBooking(Long bookingId, BookingUpdateRequest request);
}
