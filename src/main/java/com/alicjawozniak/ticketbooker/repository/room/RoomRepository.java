package com.alicjawozniak.ticketbooker.repository.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
