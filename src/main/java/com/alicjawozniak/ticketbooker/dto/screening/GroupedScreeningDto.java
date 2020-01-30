package com.alicjawozniak.ticketbooker.dto.screening;

import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder
@Data
public class GroupedScreeningDto {

    private Map<MovieDto, List<ScreeningDto>> screenings;
}
