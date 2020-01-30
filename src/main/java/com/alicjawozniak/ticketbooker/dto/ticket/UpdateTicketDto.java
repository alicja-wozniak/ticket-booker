package com.alicjawozniak.ticketbooker.dto.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import lombok.Data;

@Data
public class UpdateTicketDto {

    private Long id;

    private TicketType type;

    private Long userId;

    private Long screeningId;

    private Long seatId;
}
