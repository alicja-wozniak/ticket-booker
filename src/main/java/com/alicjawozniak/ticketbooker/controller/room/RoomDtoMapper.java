package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.dto.room.RoomDto;

public class RoomDtoMapper {

    public static RoomDto toDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .build();
    }

}
