package com.alicjawozniak.ticketbooker.repository.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Long, Movie> {
}
