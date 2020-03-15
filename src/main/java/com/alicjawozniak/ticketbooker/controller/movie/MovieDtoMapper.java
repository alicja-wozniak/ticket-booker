package com.alicjawozniak.ticketbooker.controller.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;

public class MovieDtoMapper {

    public static MovieDto toDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .build();
    }

}
