package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.controller.DtoMapperConfig;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.SeatDto;
import org.mapstruct.Mapper;

@Mapper(config = DtoMapperConfig.class)
public interface SeatDtoMapper {

    SeatDto toDto(Seat seat);

}
