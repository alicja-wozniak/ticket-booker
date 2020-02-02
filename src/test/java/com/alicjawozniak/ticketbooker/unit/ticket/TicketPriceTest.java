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
        Double price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(25);
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
        Double price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(18);
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
        Double price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(12.5);
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
        Double price = ticket.getPrice();

        //then
        assertThat(price).isNotNull();
        assertThat(price).isEqualTo(
                (2 * 25) + (2 * 18) + (3 * 12.5)
        );
    }
}
