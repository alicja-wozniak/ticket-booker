package com.alicjawozniak.ticketbooker.controller.screening;

import com.alicjawozniak.ticketbooker.controller.movie.MovieDtoMapper;
import com.alicjawozniak.ticketbooker.controller.room.RoomDtoMapper;
import com.alicjawozniak.ticketbooker.controller.room.SeatDtoMapper;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.grouped.GroupedScreeningWrapperDto;
import com.alicjawozniak.ticketbooker.dto.screening.grouped.SingleGroupedScreeningDto;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScreeningDtoMapper {

    public static ScreeningDto toDto(Screening screening) {
        return ScreeningDto
                .builder()
                .id(screening.getId())
                .movie(MovieDtoMapper.toDto(screening.getMovie()))
                .room(RoomDtoMapper.toDto(screening.getRoom()))
                .freeSeats(
                        screening.getFreeSeats()
                        .stream()
                        .map(SeatDtoMapper::toDto)
                        .collect(Collectors.toList())
                )
                .startTime(screening.getStartTime())
                .endTime(screening.getEndTime())
                .build();
    }

    public static Page<ScreeningDto> toDto(Page<Screening> screeningPage) {
        return screeningPage
                .map(ScreeningDtoMapper::toDto);
    }

    public static GroupedScreeningWrapperDto toGroupedScreeningDto(Page<Screening> screeningPage) {
        return GroupedScreeningWrapperDto.builder()
                        .groupedScreenings(
                                toSingleScreeningDtoList(screeningPage
                                        .getContent()
                                        .stream()
                                        .map(ScreeningDtoMapper::toDto)
                                        .collect(Collectors.groupingBy(
                                                ScreeningDto::getMovie
                                        ))
                                )
                        )
                        .build();
    }

    public static List<SingleGroupedScreeningDto> toSingleScreeningDtoList(Map<MovieDto, List<ScreeningDto>> groupedScreeningsMap) {
        return groupedScreeningsMap
                .entrySet()
                .stream()
                .map(k -> new SingleGroupedScreeningDto(k.getKey(), getSortedScreenings(k.getValue())))
                .sorted(Comparator.comparing(SingleGroupedScreeningDto::getMovieDto, getMoviesComparator()))
                .collect(Collectors.toList());
    }

    private static List<ScreeningDto> getSortedScreenings(List<ScreeningDto> screeningDtos) {
        screeningDtos.sort(getScreeningsComparator());
        return screeningDtos;
    }

    private static Comparator<ScreeningDto> getScreeningsComparator() {
        return Comparator.comparing(ScreeningDto::getStartTime);
    }

    private static Comparator<MovieDto> getMoviesComparator() {
        return Comparator.comparing(MovieDto::getTitle);
    }
}
