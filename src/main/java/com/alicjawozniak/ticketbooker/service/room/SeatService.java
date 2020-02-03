package com.alicjawozniak.ticketbooker.service.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.CreateSeatDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateSeatDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SeatService {

    Seat create(CreateSeatDto dto);

    Seat read(Long id);

    Page<Seat> readAll(Pageable pageable);

    Optional<Seat> read(Room room, long number);

    Seat update(Long id, UpdateSeatDto dto);

    void delete(Long id);

}
