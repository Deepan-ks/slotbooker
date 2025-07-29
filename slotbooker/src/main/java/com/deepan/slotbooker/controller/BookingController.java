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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final UserRepository userRepository;


    // TEMP: assume a single hardcoded player for demo
    private final Long DEMO_PLAYER_ID = 1L;

    @PostMapping
    public ResponseEntity<BookingResponse> bookSlot(@Valid @RequestBody BookingCreateRequest request) {
        User player = userRepository.findById(request.getPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        Slot slot = slotRepository.findById(request.getSlotId()).orElseThrow(() -> new IllegalArgumentException("Slot not found"));
        if (Boolean.TRUE.equals(slot.getIsBooked())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // or throw new CustomException("Slot already booked");
        }
        // Create and save booking
        Booking booking = BookingMapper.createBookingEntity(request, slot, player);
        slot.setIsBooked(true); // mark slot as booked
        slotRepository.save(slot); // persist updated slot
        Booking savedBooking = bookingRepository.save(booking);

        BookingResponse response = BookingMapper.buildBookingResponse(savedBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookingsForCurrentUser(@RequestParam Long playerId){
        User player = userRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        List<Booking> bookings = bookingRepository.findByPlayer(player);
        List<BookingResponse> responses = bookings.stream().map(BookingMapper::buildBookingResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus().name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Slot slot = booking.getSlot();
        slot.setIsBooked(false);

        slotRepository.save(slot);
        bookingRepository.save(booking);

        return ResponseEntity.noContent().build();
    }
}
