package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.CreateSeatDto;
import com.alicjawozniak.ticketbooker.dto.room.SeatDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateSeatDto;

public class SeatDtoMapper {

    public static SeatDto toDto(Seat seat) {
        return SeatDto.builder()
                .id(seat.getId())
                .number(seat.getNumber())
                .build();
    }

    public static Seat toDomain(CreateSeatDto dto) {
        return Seat.builder()
                .number(dto.getNumber())
                .build();
    }

    public static Seat toDomain(UpdateSeatDto dto) {
        return Seat.builder()
                .id(dto.getId())
                .number(dto.getNumber())
                .build();
    }
}
