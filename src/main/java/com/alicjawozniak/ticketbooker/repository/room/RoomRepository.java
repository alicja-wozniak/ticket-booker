package com.alicjawozniak.ticketbooker.repository.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Long, Room> {
}
