package com.deepan.slotbooker.controller;


import com.deepan.slotbooker.dto.bookingDTO.BookingCreateRequest;
import com.deepan.slotbooker.dto.bookingDTO.BookingResponse;
import com.deepan.slotbooker.dto.bookingDTO.BookingUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Booking-related API endpoints.
 * All endpoints are now prefixed with "/api/v1".
 */
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Books a slot for a player.
     *
     * @param playerId The ID of the player making the booking.
     * @param request The DTO containing the slot ID.
     * @return A ResponseEntity with the created booking and HTTP status 201 (Created).
     */
    @PostMapping("/{playerId}")
    public ResponseEntity<BookingResponse> bookSlot(@PathVariable Long playerId, @Valid @RequestBody BookingCreateRequest request) {
        try {
            BookingResponse response = bookingService.bookSlot(playerId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Cancels a booking by its ID.
     *
     * @param bookingId The ID of the booking to cancel.
     * @return A ResponseEntity with HTTP status 200 (OK) on success, or 404 (Not Found) or 400 (Bad Request).
     */
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        try {
            Boolean isCancelled = bookingService.cancelBooking(bookingId);
            if (isCancelled) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a single booking by its ID.
     *
     * @param bookingId The ID of the booking.
     * @return A ResponseEntity with the booking details and HTTP status 200 (OK), or 404 (Not Found).
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {
        try {
            BookingResponse response = bookingService.getBookingById(bookingId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all bookings for a specific player.
     *
     * @param playerId The ID of the player.
     * @return A ResponseEntity with a list of bookings and HTTP status 200 (OK).
     */
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<BookingResponse>> getBookingsForPlayer(@PathVariable Long playerId) {
        try {
            List<BookingResponse> bookings = bookingService.getBookingsForPlayer(playerId);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing booking.
     *
     * @param bookingId The ID of the booking to update.
     * @param request The DTO with fields to update.
     * @return A ResponseEntity with the updated booking and HTTP status 200 (OK), or 404 (Not Found).
     */
    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long bookingId, @Valid @RequestBody BookingUpdateRequest request) {
        try {
            BookingResponse response = bookingService.updateBooking(bookingId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
