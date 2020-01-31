package com.alicjawozniak.ticketbooker.dto.screening.grouped;

import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
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
public class SingleGroupedScreeningDto {

    private MovieDto movieDto;

    private List<ScreeningDto> screenings;
}
