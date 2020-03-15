package com.alicjawozniak.ticketbooker.service.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.movie.QMovie;
import com.alicjawozniak.ticketbooker.dto.movie.CreateMovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.UpdateMovieDto;
import com.alicjawozniak.ticketbooker.repository.movie.MovieRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie create(CreateMovieDto dto) {
        return movieRepository.save(
                toDomain(dto)
        );
    }

    @Override
    public Movie read(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Movie> readAll(
            String title,
            Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMovie qMovie = QMovie.movie;
        Optional.ofNullable(title)
                .ifPresent(title1 -> booleanBuilder.and(qMovie.title.like(title1)));

        return movieRepository.findAll(booleanBuilder, pageable);
    }

    @Override
    public Movie update(Long id, UpdateMovieDto dto) {
        return movieRepository.save(
                toDomain(id, dto)
        );
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    private Movie toDomain(CreateMovieDto dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .build();
    }

    private Movie toDomain(Long id, UpdateMovieDto dto) {
        return Movie.builder()
                .id(id)
                .title(dto.getTitle())
                .build();
    }
}
