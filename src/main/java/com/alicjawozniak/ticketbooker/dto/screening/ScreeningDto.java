package com.alicjawozniak.ticketbooker.dto.screening;

import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import com.alicjawozniak.ticketbooker.dto.room.RoomDto;
import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScreeningDto {

    private Long id;

    private MovieDto movie;

    private RoomDto room;

    private List<TicketDto> soldTickets;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
