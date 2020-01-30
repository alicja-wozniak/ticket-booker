package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.dto.room.CreateRoomDto;
import com.alicjawozniak.ticketbooker.dto.room.RoomDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateRoomDto;

public class RoomDtoMapper {

    public static RoomDto toDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .build();
    }

    public static Room toDomain(CreateRoomDto dto) {
        return Room.builder()
                .number(dto.getNumber())
                .build();
    }

    public static Room toDomain(UpdateRoomDto dto) {
        return Room.builder()
                .id(dto.getId())
                .number(dto.getNumber())
                .build();
    }
}
