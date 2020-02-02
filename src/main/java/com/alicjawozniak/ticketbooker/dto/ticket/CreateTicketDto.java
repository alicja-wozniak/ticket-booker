package com.alicjawozniak.ticketbooker.dto.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateTicketDto {

    private Map<TicketType,Integer> typeQuantities;

    private String userName;

    private String userSurname;

    private Long screeningId;

    private List<Long> seatIds;
}
