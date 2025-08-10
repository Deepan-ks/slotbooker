package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.booking.BookingCreateRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new booking
     */
    @PreAuthorize("hasRole('PLAYER')")
    @PostMapping
    public ResponseEntity<BookingResponse> bookSlot(@Valid @RequestBody BookingCreateRequest request) {
        BookingResponse response = bookingService.bookSlot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all bookings for player
     */
    @PreAuthorize("hasRole('PLAYER')")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookingsForCurrentUser(@RequestParam Long playerId){
        List<BookingResponse> responses = bookingService.getBookingsForPlayerId(playerId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Delete a booking
     */
    @PreAuthorize("hasRole('PLAYER')")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId){
        Boolean isDeleted = bookingService.deleteBookingForId(bookingId);
        if (Boolean.FALSE.equals(isDeleted)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }
}
