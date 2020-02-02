package com.alicjawozniak.ticketbooker.domain.ticket;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private Map<TicketType,Integer> typeQuantities;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userSurname;

    @ManyToOne(optional = false)
    private Screening screening;

    @ManyToMany
    private List<Seat> seats;

    private LocalDateTime paymentDeadline;

    public Double getPrice() {
        return typeQuantities.entrySet()
                .stream()
                .mapToDouble(
                        entry -> entry.getKey().getPrice() * entry.getValue()
                )
                .sum();
    }
}
