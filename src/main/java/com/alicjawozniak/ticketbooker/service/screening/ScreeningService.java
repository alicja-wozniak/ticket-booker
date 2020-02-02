package com.alicjawozniak.ticketbooker.service.screening;

import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.CreateScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.UpdateScreeningDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ScreeningService {

    Screening create(CreateScreeningDto dto);

    Screening read(Long id);

    Page<Screening> readAll(LocalDateTime minStartTime, LocalDateTime maxStartTime, Long movieId, Pageable pageable);

    Screening update(Long id, UpdateScreeningDto dto);

    void delete(Long id);
}
