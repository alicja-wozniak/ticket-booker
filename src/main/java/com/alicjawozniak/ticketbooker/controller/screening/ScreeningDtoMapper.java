package com.alicjawozniak.ticketbooker.controller.screening;

import com.alicjawozniak.ticketbooker.controller.movie.MovieDtoMapper;
import com.alicjawozniak.ticketbooker.controller.room.RoomDtoMapper;
import com.alicjawozniak.ticketbooker.controller.ticket.TicketDtoMapper;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.GroupedScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScreeningDtoMapper {

    public static ScreeningDto toDto(Screening screening) {
        return ScreeningDto
                .builder()
                .id(screening.getId())
                .movie(MovieDtoMapper.toDto(screening.getMovie()))
                .room(RoomDtoMapper.toDto(screening.getRoom()))
                .soldTickets(
                        Optional.ofNullable(screening.getSoldTickets())
                                .map(soldTickets -> soldTickets
                                        .stream()
                                        .map(TicketDtoMapper::toDto)
                                        .collect(Collectors.toList()))
                                .orElse(Collections.emptyList())
                )
                .startTime(screening.getStartTime())
                .endTime(screening.getEndTime())
                .build();
    }

    public static Page<ScreeningDto> toDto(Page<Screening> screeningPage) {
        return screeningPage
                .map(ScreeningDtoMapper::toDto);
    }

    public static GroupedScreeningDto toGroupedScreeningDto(Page<Screening> screeningPage) {
        return
                GroupedScreeningDto.builder()
                        .screenings(
                                screeningPage
                                        .getContent()
                                        .stream()
                                        .map(ScreeningDtoMapper::toDto)
                                        .collect(Collectors.groupingBy(
                                                ScreeningDto::getMovie
                                        ))
                        )
                        .build();
    }

}
