package com.alicjawozniak.ticketbooker.repository.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Long, Ticket> {
}
