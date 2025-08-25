package com.deepan.slotbooker.dto.bookingDTO;

import com.deepan.slotbooker.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateRequest {
    private BookingStatus status;
}
