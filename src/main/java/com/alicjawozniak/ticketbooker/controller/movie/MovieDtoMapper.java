package com.alicjawozniak.ticketbooker.controller.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.dto.movie.CreateMovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.UpdateMovieDto;

public class MovieDtoMapper {

    public static MovieDto toDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .build();
    }

    public static Movie toDomain(CreateMovieDto dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .build();
    }

    public static Movie toDomain(UpdateMovieDto dto) {
        return Movie.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .build();
    }
}
