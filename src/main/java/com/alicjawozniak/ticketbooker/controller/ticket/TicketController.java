package com.alicjawozniak.ticketbooker.controller.ticket;

import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import com.alicjawozniak.ticketbooker.service.ticket.TicketService;
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
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDto create(@RequestBody @Valid CreateTicketDto dto){
        return TicketDtoMapper.toDto(
                ticketService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public TicketDto read(@PathVariable("id") Long id){
        return TicketDtoMapper.toDto(
                ticketService.read(id)
        );
    }

    @GetMapping
    public Page<TicketDto> readAll(
            @RequestParam(value = "userSurname", required = false) String userSurname,
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ticketService.readAll(userSurname, pageable)
                .map(TicketDtoMapper::toDto);
    }

    @PutMapping("/{id}")
    public TicketDto update(@PathVariable("id") Long id, @RequestBody UpdateTicketDto dto){
        return TicketDtoMapper.toDto(
                ticketService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        ticketService.delete(id);
    }
}
