package com.alicjawozniak.ticketbooker.controller.room;

import com.alicjawozniak.ticketbooker.dto.room.CreateRoomDto;
import com.alicjawozniak.ticketbooker.dto.room.RoomDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateRoomDto;
import com.alicjawozniak.ticketbooker.service.room.RoomService;
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
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDto create(@RequestBody @Valid CreateRoomDto dto){
        return RoomDtoMapper.toDto(
                roomService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public RoomDto read(@PathVariable("id") Long id){
        return RoomDtoMapper.toDto(
                roomService.read(id)
        );
    }

    @GetMapping
    public Page<RoomDto> readAll(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return roomService.readAll(pageable)
                .map(RoomDtoMapper::toDto);
    }

    @PutMapping("/{id}")
    public RoomDto update(@PathVariable("id") Long id, @RequestBody UpdateRoomDto dto){
        return RoomDtoMapper.toDto(
                roomService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        roomService.delete(id);
    }
}
