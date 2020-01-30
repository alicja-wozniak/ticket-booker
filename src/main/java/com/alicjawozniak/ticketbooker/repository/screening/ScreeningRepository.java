package com.alicjawozniak.ticketbooker.repository.screening;

import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long>, QuerydslPredicateExecutor<Screening> {
}
