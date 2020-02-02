package com.alicjawozniak.ticketbooker.service.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.domain.ticket.QTicket;
import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import com.alicjawozniak.ticketbooker.repository.ticket.TicketRepository;
import com.alicjawozniak.ticketbooker.service.movie.MovieService;
import com.alicjawozniak.ticketbooker.service.room.SeatService;
import com.alicjawozniak.ticketbooker.service.screening.ScreeningService;
import com.alicjawozniak.ticketbooker.service.user.UserService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final ScreeningService screeningService;

    private final SeatService seatService;

    private final UserService userService;

    private final TicketRepository ticketRepository;

    public Ticket create(CreateTicketDto dto) {
        return ticketRepository.save(
                toDomain(dto)
        );
    }

    public Ticket read(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Ticket> readAll(
            Long userId,
            Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QTicket qTicket = QTicket.ticket;
        Optional.ofNullable(userId)
                .ifPresent(userId1 -> booleanBuilder.and(qTicket.user.id.eq(userId1)));

        return ticketRepository.findAll(booleanBuilder, pageable);
    }

    public Ticket update(Long id, UpdateTicketDto dto) {
        return ticketRepository.save(
                toDomain(id, dto)
        );
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    private Ticket toDomain(CreateTicketDto dto) {
        return Ticket.builder()
                .type(dto.getType())
                .user(userService.read(dto.getUserId()))
                .screening(screeningService.read(dto.getScreeningId()))
                .seat(seatService.read(dto.getSeatId()))
                .build();
    }

    private Ticket toDomain(Long id, UpdateTicketDto dto) {
        return Ticket.builder()
                .id(id)
                .type(dto.getType())
                .user(userService.read(dto.getUserId()))
                .screening(screeningService.read(dto.getScreeningId()))
                .seat(seatService.read(dto.getSeatId()))
                .build();
    }
}
