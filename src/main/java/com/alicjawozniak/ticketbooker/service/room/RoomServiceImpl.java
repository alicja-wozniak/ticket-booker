package com.alicjawozniak.ticketbooker.service.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.dto.room.CreateRoomDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateRoomDto;
import com.alicjawozniak.ticketbooker.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    
    private final RoomRepository roomRepository;

    @Override
    public Room create(CreateRoomDto dto) {
        return roomRepository.save(
                toDomain(dto)
        );
    }

    @Override
    public Room read(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Room> readAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Room update(Long id, UpdateRoomDto dto) {
        return roomRepository.save(
                toDomain(id, dto)
        );
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    private Room toDomain(CreateRoomDto dto) {
        return Room.builder()
                .number(dto.getNumber())
                .build();
    }

    private Room toDomain(Long id, UpdateRoomDto dto) {
        return Room.builder()
                .id(id)
                .number(dto.getNumber())
                .build();
    }
}
