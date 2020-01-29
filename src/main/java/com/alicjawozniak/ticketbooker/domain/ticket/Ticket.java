package com.alicjawozniak.ticketbooker.domain.ticket;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.domain.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @ManyToOne
    private User user;

    @ManyToOne(optional = false)
    private Screening screening;

    @ManyToOne(optional = false)
    private Seat seat;
}
