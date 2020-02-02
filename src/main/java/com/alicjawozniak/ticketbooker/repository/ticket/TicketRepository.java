package com.alicjawozniak.ticketbooker.repository.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, QuerydslPredicateExecutor<Ticket> {
}
