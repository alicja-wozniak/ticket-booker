package com.alicjawozniak.ticketbooker.repository.room;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Long, Seat> {
}
