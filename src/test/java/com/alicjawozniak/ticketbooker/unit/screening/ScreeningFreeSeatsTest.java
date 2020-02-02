package com.alicjawozniak.ticketbooker.unit.screening;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class ScreeningFreeSeatsTest {

    @Test
    public void canReadNotEmptyFreeSeats() {
        //given
        List<Seat> seats = getSeats();
        Seat takenSeat = seats.get(0);
        Screening screening =  Screening.builder()
                .room(Room.builder()
                        .number("Room 1")
                        .seats(seats)
                        .build())
                .soldTickets(
                        Collections.singletonList(Ticket.builder()
                                .seats(Collections.singletonList(takenSeat))
                                .build())
                )
                .build();

        //when
        List<Seat> freeSeats = screening.getFreeSeats();

        //then
        assertThat(freeSeats).isNotNull();
        assertThat(freeSeats).isNotEmpty();
        assertThat(freeSeats).hasSize(2);
        assertThat(freeSeats).noneMatch(seat -> seat.getId().equals(takenSeat.getId()));

    }

    @Test
    public void canReadEmptyFreeSeatsFromSeparateTickets() {
        //given
        List<Seat> seats = getSeats();
        List<Ticket> tickets = seats.stream()
                .map(seat -> Ticket.builder()
                        .seats(Collections.singletonList(seat))
                        .build())
                .collect(Collectors.toList());
        Screening screening =  Screening.builder()
                .room(Room.builder()
                        .number("Room 1")
                        .seats(seats)
                        .build())
                .soldTickets(tickets)
                .build();

        //when
        List<Seat> freeSeats = screening.getFreeSeats();

        //then
        assertThat(freeSeats).isNotNull();
        assertThat(freeSeats).isEmpty();

    }

    @Test
    public void canReadEmptyFreeSeatsFromOneTicket() {
        //given
        List<Seat> seats = getSeats();
        Ticket ticket = Ticket.builder()
                        .seats(seats)
                        .build();
        Screening screening =  Screening.builder()
                .room(Room.builder()
                        .number("Room 1")
                        .seats(seats)
                        .build())
                .soldTickets(Collections.singletonList(ticket))
                .build();

        //when
        List<Seat> freeSeats = screening.getFreeSeats();

        //then
        assertThat(freeSeats).isNotNull();
        assertThat(freeSeats).isEmpty();

    }

    @Test
    public void canReadAllFreeSeats() {
        //given
        List<Seat> seats = getSeats();
        Screening screening =  Screening.builder()
                .room(Room.builder()
                        .number("Room 1")
                        .seats(seats)
                        .build())
                .soldTickets(Collections.emptyList())
                .build();

        //when
        List<Seat> freeSeats = screening.getFreeSeats();

        //then
        assertThat(freeSeats).isNotNull();
        assertThat(freeSeats).isNotEmpty();
        assertThat(freeSeats).hasSize(3);
        List<Long> seatIds = seats.stream().map(Seat::getId).collect(Collectors.toList());
        assertThat(freeSeats).allMatch(seat -> seatIds.contains(seat.getId()));
    }


    private List<Seat> getSeats() {
        return new ArrayList<>(
                Arrays.asList(
                        Seat.builder()
                                .id(1L)
                                .number("Seat 1")
                                .build(),
                        Seat.builder()
                                .id(2L)
                                .number("Seat 2")
                                .build(),
                        Seat.builder()
                                .id(3L)
                                .number("Seat 3")
                                .build()
                )
        );
    }
}
