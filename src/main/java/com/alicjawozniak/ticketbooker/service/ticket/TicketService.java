package com.alicjawozniak.ticketbooker.service.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.QTicket;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import com.alicjawozniak.ticketbooker.exception.ticket.TooLateReservationException;
import com.alicjawozniak.ticketbooker.repository.ticket.TicketRepository;
import com.alicjawozniak.ticketbooker.service.room.SeatService;
import com.alicjawozniak.ticketbooker.service.screening.ScreeningService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private static final int HOURS_TO_COMPLETE_PAYMENT = 2;

    private final ScreeningService screeningService;

    private final SeatService seatService;

    private final TicketRepository ticketRepository;

    public Ticket create(CreateTicketDto dto) {
        Ticket ticket = toDomain(dto);
        validate(ticket);
        return ticketRepository.save(ticket);
    }

    public Ticket read(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Ticket> readAll(
            String userSurname,
            Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QTicket qTicket = QTicket.ticket;
        Optional.ofNullable(userSurname)
                .ifPresent(userSurname1 -> booleanBuilder.and(qTicket.userSurname.like(userSurname)));

        return ticketRepository.findAll(booleanBuilder, pageable);
    }

    public Ticket update(Long id, UpdateTicketDto dto) {
        return ticketRepository.findById(id)
                .map(ticket -> merge(ticket, dto))
                .map(ticketRepository::save)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    private Ticket toDomain(CreateTicketDto dto) {
        return Ticket.builder()
                .typeQuantities(dto.getTypeQuantities())
                .userName(dto.getUserName())
                .userSurname(dto.getUserSurname())
                .screening(screeningService.read(dto.getScreeningId()))
                .seats(
                        dto.getSeatIds()
                        .stream()
                        .map(seatService::read)
                        .collect(Collectors.toList())
                )
                .paymentDeadline(LocalDateTime.now().plusHours(HOURS_TO_COMPLETE_PAYMENT))
                .build();
    }

    private Ticket merge(Ticket ticket, UpdateTicketDto dto) {
        return ticket.toBuilder()
                .typeQuantities(dto.getTypeQuantities())
                .userName(dto.getUserName())
                .userSurname(dto.getUserSurname())
                .screening(screeningService.read(dto.getScreeningId()))
                .seats(
                        dto.getSeatIds()
                                .stream()
                                .map(seatService::read)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private void validate(Ticket ticket) {
        if (LocalDateTime.now().isAfter(
                ticket.getScreening().getStartTime().minusMinutes(15L))
        ) {
            throw new TooLateReservationException();
        }
    }
}
