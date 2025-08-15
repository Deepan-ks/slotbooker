package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.booking.BookingRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.enums.BookingStatus;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.model.User;

public class BookingMapper {

    private BookingMapper() {}

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
                .sportName(booking.getSlot().getFacility().getSport().getSportName())
                .playerId(booking.getPlayer().getUserId())
                .playerName(booking.getPlayer().getUserName())
                .status(booking.getStatus())
                .build();
    }

    /**
     * Creates a Booking entity from request, with reserved Slot and Player.
     */
    public static Booking createBookingEntity(BookingRequest request, Slot slot, User player) {
        return Booking.builder()
                .slot(slot)
                .player(player)
                .status(BookingStatus.BOOKED)
                .build();
    }
}
