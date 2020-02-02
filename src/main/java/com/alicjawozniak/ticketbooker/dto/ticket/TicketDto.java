package com.alicjawozniak.ticketbooker.dto.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import com.alicjawozniak.ticketbooker.dto.room.SeatDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TicketDto {

    private Long id;

    private TicketType type;

    private String userName;

    private String userSurname;

    private ScreeningDto screening;

    private List<SeatDto> seats;
}
