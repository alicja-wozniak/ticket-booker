package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.controller.DtoMapperConfig;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.dto.room.RoomDto;
import org.mapstruct.Mapper;

@Mapper(config = DtoMapperConfig.class)
public interface RoomDtoMapper {

    RoomDto toDto(Room room);

}
