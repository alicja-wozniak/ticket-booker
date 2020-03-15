package com.alicjawozniak.ticketbooker.dto.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;

import java.util.List;
import java.util.Map;

public interface SeatsQuantityDto {
    Map<TicketType,Integer> getTypeQuantities();
    List<Long> getSeatIds();
}
