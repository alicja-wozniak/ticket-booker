package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.SeatDto;

public class SeatDtoMapper {

    public static SeatDto toDto(Seat seat) {
        return SeatDto.builder()
                .id(seat.getId())
                .number(seat.getNumber())
                .build();
    }

}
