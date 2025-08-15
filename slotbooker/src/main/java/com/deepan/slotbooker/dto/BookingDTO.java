package com.deepan.slotbooker.dto;

import com.deepan.slotbooker.model.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    private Long id;

    @NotNull(message = "Slot Id must be provided")
    private Long slotId;

    @NotNull(message = "Player Id must be provided")
    private Long playerId;

    private BookingStatus status;

}
