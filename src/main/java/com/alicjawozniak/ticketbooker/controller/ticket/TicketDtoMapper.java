package com.alicjawozniak.ticketbooker.controller.ticket;

import com.alicjawozniak.ticketbooker.controller.DtoMapperConfig;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;
import org.mapstruct.Mapper;

@Mapper(config = DtoMapperConfig.class)
public interface TicketDtoMapper {

    TicketDto toDto(Ticket ticket);
}
