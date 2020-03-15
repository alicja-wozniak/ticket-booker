package com.alicjawozniak.ticketbooker.controller.movie;

import com.alicjawozniak.ticketbooker.controller.DtoMapperConfig;
import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import org.mapstruct.Mapper;

@Mapper(config = DtoMapperConfig.class)
public interface MovieDtoMapper {

    MovieDto toDto(Movie movie);

}