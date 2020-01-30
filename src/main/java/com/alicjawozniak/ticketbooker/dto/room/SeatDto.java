package com.alicjawozniak.ticketbooker.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SeatDto {

    private Long id;

    private String number;

}
