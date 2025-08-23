package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.bookingDTO.BookingResponse;
import com.deepan.slotbooker.dto.facilityDTO.FacilityResponse;
import com.deepan.slotbooker.dto.slotDTO.SlotResponse;
import com.deepan.slotbooker.dto.sportDTO.SportResponse;
import com.deepan.slotbooker.dto.userDTO.UserResponse;
import com.deepan.slotbooker.dto.venueDTO.VenueResponse;
import com.deepan.slotbooker.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for mapping between Entity models and DTOs.
 * This class follows a 'build' naming convention for clarity and consistency.
 */
public class Mapper {

    /**
     * Builds a UserResponse DTO from a User entity.
     * @param user The User entity to map.
     * @return The corresponding UserResponse DTO.
     */
    public static UserResponse buildUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole(),
                user.getUserStatus()
        );
    }

    /**
     * Builds a VenueResponse DTO from a Venue entity.
     * @param venue The Venue entity to map.
     * @return The corresponding VenueResponse DTO.
     */
    public static VenueResponse buildVenueResponse(Venue venue) {
        if (venue == null) {
            return null;
        }
        return new VenueResponse(
                venue.getId(),
                venue.getName(),
                venue.getCity(),
                venue.getAddress(),
                venue.getMobileNumber(),
                venue.getOwner().getId(),
                venue.getType()
        );
    }

    /**
     * Builds a SportResponse DTO from a Sport entity.
     * @param sport The Sport entity to map.
     * @return The corresponding SportResponse DTO.
     */
    public static SportResponse buildSportResponse(Sport sport) {
        if (sport == null) {
            return null;
        }
        return new SportResponse(
                sport.getId(),
                sport.getName()
        );
    }

    /**
     * Builds a FacilityResponse DTO from a Facility entity.
     * @param facility The Facility entity to map.
     * @return The corresponding FacilityResponse DTO.
     */
    public static FacilityResponse buildFacilityResponse(Facility facility) {
        if (facility == null) {
            return null;
        }
        return new FacilityResponse(
                facility.getId(),
                facility.getName(),
                facility.getPricePerHour(),
                facility.getVenue().getId(),
                buildSportResponse(facility.getSport())
        );
    }

    /**
     * Builds a SlotResponse DTO from a Slot entity.
     * @param slot The Slot entity to map.
     * @return The corresponding SlotResponse DTO.
     */
    public static SlotResponse buildSlotResponse(Slot slot) {
        if (slot == null) {
            return null;
        }
        return new SlotResponse(
                slot.getId(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getFacility().getId(),
                slot.getIsBooked()
        );
    }

    /**
     * Builds a BookingResponse DTO from a Booking entity.
     * @param booking The Booking entity to map.
     * @return The corresponding BookingResponse DTO.
     */
    public static BookingResponse buildBookingResponse(Booking booking) {
        if (booking == null) {
            return null;
        }
        return new BookingResponse(
                booking.getId(),
                booking.getBookingTime(),
                booking.getStatus().name(),
                booking.getPlayer().getId(),
                buildSlotResponse(booking.getSlot())
        );
    }

    // List mappers
    public static List<UserResponse> buildUserResponseList(List<User> users) {
        return users.stream().map(Mapper::buildUserResponse).collect(Collectors.toList());
    }

    public static List<VenueResponse> buildVenueResponseList(List<Venue> venues) {
        return venues.stream().map(Mapper::buildVenueResponse).collect(Collectors.toList());
    }

    public static List<SportResponse> buildSportResponseList(List<Sport> sports) {
        return sports.stream().map(Mapper::buildSportResponse).collect(Collectors.toList());
    }

    public static List<FacilityResponse> buildFacilityResponseList(List<Facility> facilities) {
        return facilities.stream().map(Mapper::buildFacilityResponse).collect(Collectors.toList());
    }

    public static List<SlotResponse> buildSlotResponseList(List<Slot> slots) {
        return slots.stream().map(Mapper::buildSlotResponse).collect(Collectors.toList());
    }

    public static List<BookingResponse> buildBookingResponseList(List<Booking> bookings) {
        return bookings.stream().map(Mapper::buildBookingResponse).collect(Collectors.toList());
    }
}
