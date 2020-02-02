package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.dto.room.CreateSeatDto;
import com.alicjawozniak.ticketbooker.dto.room.SeatDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateSeatDto;
import com.alicjawozniak.ticketbooker.service.room.SeatService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeatDto create(@RequestBody @Valid CreateSeatDto dto){
        return SeatDtoMapper.toDto(
                seatService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public SeatDto read(@PathVariable("id") Long id){
        return SeatDtoMapper.toDto(
                seatService.read(id)
        );
    }

    @PutMapping("/{id}")
    public SeatDto update(@PathVariable("id") Long id, @RequestBody UpdateSeatDto dto){
        return SeatDtoMapper.toDto(
                seatService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        seatService.delete(id);
    }
}
