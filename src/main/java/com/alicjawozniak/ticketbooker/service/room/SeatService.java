package com.alicjawozniak.ticketbooker.service.room;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.CreateSeatDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateSeatDto;
import com.alicjawozniak.ticketbooker.repository.room.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class SeatService {
    
    private final SeatRepository seatRepository;

    public Seat create(CreateSeatDto dto) {
        return seatRepository.save(
                toDomain(dto)
        );
    }

    public Seat read(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Seat> readAll(Pageable pageable) {
        return seatRepository.findAll(pageable);
    }

    public Seat update(Long id, UpdateSeatDto dto) {
        return seatRepository.save(
                toDomain(id, dto)
        );
    }

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
