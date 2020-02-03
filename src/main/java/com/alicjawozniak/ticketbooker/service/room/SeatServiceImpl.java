package com.alicjawozniak.ticketbooker.service.room;

import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.CreateSeatDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateSeatDto;
import com.alicjawozniak.ticketbooker.repository.room.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    
    private final SeatRepository seatRepository;

    @Override
    public Seat create(CreateSeatDto dto) {
        return seatRepository.save(
                toDomain(dto)
        );
    }

    @Override
    public Seat read(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Seat> readAll(Pageable pageable) {
        return seatRepository.findAll(pageable);
    }

    @Override
    public Optional<Seat> read(Room room, long number) {
        return seatRepository.findByRoomAndNumber(room, number);
    }

    @Override
    public Seat update(Long id, UpdateSeatDto dto) {
        return seatRepository.save(
                toDomain(id, dto)
        );
    }

    @Override
    public void delete(Long id) {
        seatRepository.deleteById(id);
    }

    private Seat toDomain(CreateSeatDto dto) {
        return Seat.builder()
                .number(dto.getNumber())
                .build();
    }

    private Seat toDomain(Long id, UpdateSeatDto dto) {
        return Seat.builder()
                .id(id)
                .number(dto.getNumber())
                .build();
    }
}
