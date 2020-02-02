package com.alicjawozniak.ticketbooker.domain.ticket;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userSurname;

    @ManyToOne(optional = false)
    private Screening screening;

    @ManyToMany
    private List<Seat> seats;
}
