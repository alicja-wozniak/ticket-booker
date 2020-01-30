package com.alicjawozniak.ticketbooker.repository.room;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
