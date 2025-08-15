package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.BookingDTO;
import com.deepan.slotbooker.dto.booking.BookingCreateRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.enums.BookingStatus;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.model.User;

public class BookingMapper {

    /**
     * Builds an internal BookingDTO for cross-layer communication or legacy use.
     */
    public static BookingDTO mapToBookingDTO (Booking booking){
        return BookingDTO.builder()
                .id(booking.getBookingId())
                .slotId(booking.getSlot().getSlotId())
                .playerId(booking.getPlayer().getUserId())
                .status(booking.getStatus())
                .build();
    }

    /**
     * Constructs a Booking entity from BookingDTO (used internally if needed).
     */
    public static Booking mapToBookingEntity(BookingDTO bookingDTO){
        Booking booking = new Booking();
        booking.setStatus(bookingDTO.getStatus());
        return booking;
    }

    /**
     * Converts a Booking entity into a response object.
     */
    public static BookingResponse buildBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .slotId(booking.getSlot().getSlotId())
                .startTime(booking.getSlot().getStartTime())
                .endTime(booking.getSlot().getEndTime())
                .facilityName(booking.getSlot().getFacility().getFacilityName())
                .venueName(booking.getSlot().getFacility().getVenue().getVenueName())
                .playerName(booking.getPlayer().getUserName())
                .status(booking.getStatus().name())
                .build();
    }

    /**
     * Creates a Booking entity from request, with reserved Slot and Player.
     */
    public static Booking createBookingEntity(BookingCreateRequest request, Slot slot, User player) {
        return Booking.builder()
                .slot(slot)
                .player(player)
                .status(BookingStatus.BOOKED)
                .build();
    }
}
