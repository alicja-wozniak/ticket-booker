package com.alicjawozniak.ticketbooker.controller.ticket;

import com.alicjawozniak.ticketbooker.controller.room.SeatDtoMapper;
import com.alicjawozniak.ticketbooker.controller.screening.ScreeningDtoMapper;
import com.alicjawozniak.ticketbooker.controller.user.UserDtoMapper;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;

public class TicketDtoMapper {

    public static TicketDto toDto(Ticket ticket) {
        return TicketDto
                .builder()
                .id(ticket.getId())
                .type(ticket.getType())
                .user(UserDtoMapper.toDto(ticket.getUser()))
                .screening(ScreeningDtoMapper.toDto(ticket.getScreening()))
                .seat(SeatDtoMapper.toDto(ticket.getSeat()))
                .build();
    }
}
