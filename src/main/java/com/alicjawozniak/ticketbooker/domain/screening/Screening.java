package com.alicjawozniak.ticketbooker.domain.screening;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "screening")
    private List<Ticket> soldTickets;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

}
