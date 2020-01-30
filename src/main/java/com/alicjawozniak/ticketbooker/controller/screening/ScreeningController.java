package com.alicjawozniak.ticketbooker.controller.screening;

import com.alicjawozniak.ticketbooker.dto.screening.CreateScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.GroupedScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.UpdateScreeningDto;
import com.alicjawozniak.ticketbooker.service.screening.ScreeningService;
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
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScreeningDto create(@RequestBody @Valid CreateScreeningDto dto){
        return ScreeningDtoMapper.toDto(
                screeningService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public ScreeningDto read(@PathVariable("id") Long id){
        return ScreeningDtoMapper.toDto(
                screeningService.read(id)
        );
    }

    @GetMapping
    public Page<ScreeningDto> readAll(
            @RequestParam(value = "minStartTime", required = false) LocalDateTime minStartTime,
            @RequestParam(value = "maxStartTime", required = false) LocalDateTime maxStartTime,
            @RequestParam(value = "movieId", required = false) Long movieId,
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ScreeningDtoMapper.toDto(
                screeningService.readAll(minStartTime, maxStartTime, movieId, pageable)
        );
    }

    @GetMapping("/grouped-by-movie")
    public GroupedScreeningDto readAllGroupedByMovie(
            @RequestParam(value = "minStartTime") LocalDateTime minStartTime,
            @RequestParam(value = "maxStartTime") LocalDateTime maxStartTime,
            @RequestParam(value = "movieId", required = false) Long movieId
    ) {
        return ScreeningDtoMapper.toGroupedScreeningDto(
                screeningService.readAll(minStartTime, maxStartTime, movieId, Pageable.unpaged())
        );
    }

    @PutMapping("/{id}")
    public ScreeningDto update(@PathVariable("id") Long id, @RequestBody UpdateScreeningDto dto){
        return ScreeningDtoMapper.toDto(
                screeningService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        screeningService.delete(id);
    }
}
