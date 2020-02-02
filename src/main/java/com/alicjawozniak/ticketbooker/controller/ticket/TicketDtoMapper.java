package com.alicjawozniak.ticketbooker.controller.ticket;

import com.alicjawozniak.ticketbooker.controller.room.SeatDtoMapper;
import com.alicjawozniak.ticketbooker.controller.screening.ScreeningDtoMapper;
import com.alicjawozniak.ticketbooker.controller.user.UserDtoMapper;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;

import java.util.stream.Collectors;

public class TicketDtoMapper {

    public static TicketDto toDto(Ticket ticket) {
        return TicketDto
                .builder()
                .id(ticket.getId())
                .type(ticket.getType())
                .user(UserDtoMapper.toDto(ticket.getUser()))
                .screening(ScreeningDtoMapper.toDto(ticket.getScreening()))
                .seats(
                        ticket.getSeats()
                        .stream()
                        .map(SeatDtoMapper::toDto)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
