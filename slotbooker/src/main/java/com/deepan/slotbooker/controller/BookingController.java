package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.booking.BookingCreateRequest;
import com.deepan.slotbooker.dto.booking.BookingResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.BookingMapper;
import com.deepan.slotbooker.model.Booking;
import com.deepan.slotbooker.model.BookingStatus;
import com.deepan.slotbooker.model.Slot;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.BookingRepository;
import com.deepan.slotbooker.repository.SlotRepository;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> bookSlot(@Valid @RequestBody BookingCreateRequest request) {
        BookingResponse response = bookingService.bookSlot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookingsForCurrentUser(@RequestParam Long playerId){
        List<BookingResponse> responses = bookingService.getBookingsForPlayerId(playerId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId){
        Boolean isDeleted = bookingService.deleteBookingForId(bookingId);
        if (Boolean.FALSE.equals(isDeleted)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }
}
