package com.alicjawozniak.ticketbooker.unit.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class TicketPriceTest {

    @Test
    public void canReadPriceFromSingleAdultSeat() {
        //given
        Ticket ticket = Ticket.builder()
                .typeQuantities(
                        Map.of(
                                TicketType.ADULT, 1
                        )
                )
                .build();

        //when
        Long price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(2500);
    }

    @Test
    public void canReadPriceFromSingleStudentSeat() {
        //given
        Ticket ticket = Ticket.builder()
                .typeQuantities(
                        Map.of(
                                TicketType.STUDENT, 1
                        )
                )
                .build();

        //when
        Long price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(1800);
    }

    @Test
    public void canReadPriceFromSingleChildSeat() {
        //given
        Ticket ticket = Ticket.builder()
                .typeQuantities(
                        Map.of(
                                TicketType.CHILD, 1
                        )
                )
                .build();

        //when
        Long price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(1250);
    }

    @Test
    public void canReadPriceFromMultipleSeats() {
        //given
        Ticket ticket = Ticket.builder()
                .typeQuantities(
                        Map.of(
                                TicketType.ADULT, 2,
                                TicketType.STUDENT, 2,
                                TicketType.CHILD, 3
                        )
                )
                .build();

        //when
        Long price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(
                (2 * 2500) + (2 * 1800) + (3 * 1250)
        );
    }
}
