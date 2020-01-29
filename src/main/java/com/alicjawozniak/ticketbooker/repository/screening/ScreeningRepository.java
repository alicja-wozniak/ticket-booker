package com.alicjawozniak.ticketbooker.repository.screening;

import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Long, Screening> {
}
