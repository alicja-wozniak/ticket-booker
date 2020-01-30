package com.alicjawozniak.ticketbooker.service.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.repository.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie read(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
