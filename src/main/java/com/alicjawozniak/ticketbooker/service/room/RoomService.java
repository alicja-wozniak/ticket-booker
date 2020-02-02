package com.alicjawozniak.ticketbooker.service.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.dto.room.CreateRoomDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateRoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {

    Room create(CreateRoomDto dto);

    Room read(Long id);

    Page<Room> readAll(Pageable pageable);

    Room update(Long id, UpdateRoomDto dto);

    void delete(Long id);
}
