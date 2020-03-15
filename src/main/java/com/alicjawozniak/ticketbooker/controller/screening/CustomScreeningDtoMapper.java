package com.alicjawozniak.ticketbooker.controller.screening;

import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.grouped.GroupedScreeningWrapperDto;
import com.alicjawozniak.ticketbooker.dto.screening.grouped.SingleGroupedScreeningDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomScreeningDtoMapper {

    private final ScreeningDtoMapper screeningDtoMapper;

    public Page<ScreeningDto> toDto(Page<Screening> screeningPage) {
        return screeningPage
                .map(screeningDtoMapper::toDto);
    }

    public GroupedScreeningWrapperDto toGroupedScreeningDto(Page<Screening> screeningPage) {
        return GroupedScreeningWrapperDto.builder()
                        .groupedScreenings(
                                toSingleScreeningDtoList(screeningPage
                                        .getContent()
                                        .stream()
                                        .map(screeningDtoMapper::toDto)
                                        .collect(Collectors.groupingBy(
                                                ScreeningDto::getMovie
                                        ))
                                )
                        )
                        .build();
    }

    public List<SingleGroupedScreeningDto> toSingleScreeningDtoList(Map<MovieDto, List<ScreeningDto>> groupedScreeningsMap) {
        return groupedScreeningsMap
                .entrySet()
                .stream()
                .map(k -> new SingleGroupedScreeningDto(k.getKey(), getSortedScreenings(k.getValue())))
                .sorted(Comparator.comparing(SingleGroupedScreeningDto::getMovieDto, getMoviesComparator()))
                .collect(Collectors.toList());
    }

    private List<ScreeningDto> getSortedScreenings(List<ScreeningDto> screeningDtos) {
        screeningDtos.sort(getScreeningsComparator());
        return screeningDtos;
    }

    private Comparator<ScreeningDto> getScreeningsComparator() {
        return Comparator.comparing(ScreeningDto::getStartTime);
    }

    private Comparator<MovieDto> getMoviesComparator() {
        return Comparator.comparing(MovieDto::getTitle);
    }
}
