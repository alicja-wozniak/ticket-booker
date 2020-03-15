package com.alicjawozniak.ticketbooker.service.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.dto.movie.CreateMovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.UpdateMovieDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    Movie create(CreateMovieDto dto);

    Movie read(Long id);

    Page<Movie> readAll(String title, Pageable pageable);

    Movie update(Long id, UpdateMovieDto dto);

    void delete(Long id);
}
