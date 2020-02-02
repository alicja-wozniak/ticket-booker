package com.alicjawozniak.ticketbooker.dto.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateTicketDto {

    private Long id;

    private TicketType type;

    private String userName;

    private String userSurname;

    private Long screeningId;

    private List<Long> seatIds;
}
