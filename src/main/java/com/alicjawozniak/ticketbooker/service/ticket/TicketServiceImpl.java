package com.alicjawozniak.ticketbooker.service.ticket;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.ticket.QTicket;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import com.alicjawozniak.ticketbooker.exception.ticket.SeatTakenException;
import com.alicjawozniak.ticketbooker.exception.ticket.SingleSeatLeftException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private static final int HOURS_TO_COMPLETE_PAYMENT = 2;
    private static final int MINUTES_TO_CREATE_TICKET_BEFORE_SCREENING = 15;

    private final ScreeningService screeningService;

    private final SeatService seatService;

    private final TicketRepository ticketRepository;

    @Override
    public Ticket create(CreateTicketDto dto) {
        Ticket ticket = toDomain(dto);
        validate(ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket read(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Ticket> readAll(
            String userSurname,
            Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QTicket qTicket = QTicket.ticket;
        Optional.ofNullable(userSurname)
                .ifPresent(userSurname1 -> booleanBuilder.and(qTicket.userSurname.like(userSurname)));

        return ticketRepository.findAll(booleanBuilder, pageable);
    }

    @Override
    public Ticket update(Long id, UpdateTicketDto dto) {
        return ticketRepository.findById(id)
                .map(ticket -> merge(ticket, dto))
                .map(ticketRepository::save)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
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
        if (isAfterReservationDeadline(ticket)) {
            throw new TooLateReservationException();
        }

        List<Long> anySeatsAlreadyTaken = getAnySeatsAlreadyTaken(ticket);
        if (!anySeatsAlreadyTaken.isEmpty()) {
            throw new SeatTakenException(anySeatsAlreadyTaken);
        }

        validateIfAnySingleSeatLeft(ticket);
    }

    private boolean isAfterReservationDeadline(Ticket ticket) {
        return LocalDateTime.now().isAfter(
                ticket.getScreening().getStartTime().minusMinutes(MINUTES_TO_CREATE_TICKET_BEFORE_SCREENING));
    }

    private List<Long> getAnySeatsAlreadyTaken(Ticket ticket) {
        return ticket.getSeats()
                .stream()
                .filter(this::isSeatTaken)
                .map(Seat::getId)
                .collect(Collectors.toList());
    }

    private void validateIfAnySingleSeatLeft(Ticket ticket) {
        Room room = ticket.getScreening().getRoom();
        ticket.getSeats()
                .forEach(
                        seat -> {
                            Long seatNumber = seat.getNumber();
                            validateSingleLeftSeats(room, seatNumber - 1, seatNumber - 2);
                            validateSingleLeftSeats(room, seatNumber - 1, seatNumber - 2);
                        }
                );
    }

    private void validateSingleLeftSeats(Room room, long firstNearestSeatNumber, long secondNearestSeatNumber) {
        Optional<Seat> firstNearestSeat = seatService.read(room, firstNearestSeatNumber);
        Optional<Seat> secondNearestSeat = seatService.read(room, secondNearestSeatNumber);
        if (firstNearestSeat.isPresent() && secondNearestSeat.isPresent()
                && !isSeatTaken(firstNearestSeat.get()) && isSeatTaken(secondNearestSeat.get())) {
            throw new SingleSeatLeftException();
        }
    }



    private boolean isSeatTaken(Seat seat) {
        return ticketRepository.existsBySeatsContaining(seat);
    }
}
