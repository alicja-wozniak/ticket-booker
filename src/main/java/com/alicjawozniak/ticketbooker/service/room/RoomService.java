package com.alicjawozniak.ticketbooker.service.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoomService {
    
    private final RoomRepository roomRepository;

    public Room read(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
