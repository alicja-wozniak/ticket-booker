package com.alicjawozniak.ticketbooker.service.screening;

import com.alicjawozniak.ticketbooker.domain.screening.QScreening;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.CreateScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.UpdateScreeningDto;
import com.alicjawozniak.ticketbooker.repository.screening.ScreeningRepository;
import com.alicjawozniak.ticketbooker.service.movie.MovieService;
import com.alicjawozniak.ticketbooker.service.room.RoomService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningRepository screeningRepository;

    public Screening create(CreateScreeningDto dto) {
        return screeningRepository.save(
                toDomain(dto)
        );
    }

    public Screening read(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Screening> readAll(
            LocalDateTime minStartTime,
            LocalDateTime maxStartTime,
            Long movieId,
            Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QScreening qScreening = QScreening.screening;
        Optional.ofNullable(minStartTime)
                .ifPresent(time -> booleanBuilder.and(qScreening.startTime.after(time)));
        Optional.ofNullable(maxStartTime)
                .ifPresent(time -> booleanBuilder.and(qScreening.startTime.before(time)));
        Optional.ofNullable(movieId)
                .ifPresent(movieId1 -> booleanBuilder.and(qScreening.movie.id.eq(movieId1)));

        return screeningRepository.findAll(booleanBuilder, pageable);
    }

    public Screening update(Long id, UpdateScreeningDto dto) {
        return screeningRepository.save(
                toDomain(id, dto)
        );
    }

    public void delete(Long id) {
        screeningRepository.deleteById(id);
    }

    private Screening toDomain(CreateScreeningDto dto) {
        return Screening.builder()
                .movie(movieService.read(dto.getMovieId()))
                .room(roomService.read(dto.getRoomId()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    private Screening toDomain(Long id, UpdateScreeningDto dto) {
        return Screening.builder()
                .id(id)
                .movie(movieService.read(dto.getMovieId()))
                .room(roomService.read(dto.getRoomId()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
