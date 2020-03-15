package com.alicjawozniak.ticketbooker.controller.movie;

import com.alicjawozniak.ticketbooker.dto.movie.CreateMovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.UpdateMovieDto;
import com.alicjawozniak.ticketbooker.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieDtoMapper movieDtoMapper;
    private final MovieService movieService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody @Valid CreateMovieDto dto){
        return movieDtoMapper.toDto(
                movieService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public MovieDto read(@PathVariable("id") Long id){
        return movieDtoMapper.toDto(
                movieService.read(id)
        );
    }

    @GetMapping
    public Page<MovieDto> readAll(
            @RequestParam(value = "title", required = false) String title,
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return movieService.readAll(title, pageable)
                .map(movieDtoMapper::toDto);
    }

    @PutMapping("/{id}")
    public MovieDto update(@PathVariable("id") Long id, @RequestBody UpdateMovieDto dto){
        return movieDtoMapper.toDto(
                movieService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        movieService.delete(id);
    }
}
