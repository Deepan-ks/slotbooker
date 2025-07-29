package com.deepan.slotbooker.dto.sport;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportResponse {
    private Long id;
    private String name;
}