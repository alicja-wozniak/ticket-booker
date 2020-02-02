package com.alicjawozniak.ticketbooker.service.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {

    Ticket create(CreateTicketDto dto);

    Ticket read(Long id);

    Page<Ticket> readAll(String userSurname, Pageable pageable);

    Ticket update(Long id, UpdateTicketDto dto);

    void delete(Long id);
}
