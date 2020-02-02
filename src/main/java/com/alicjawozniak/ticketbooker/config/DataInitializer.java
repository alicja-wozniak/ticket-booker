package com.alicjawozniak.ticketbooker.config;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import com.alicjawozniak.ticketbooker.repository.movie.MovieRepository;
import com.alicjawozniak.ticketbooker.repository.room.RoomRepository;
import com.alicjawozniak.ticketbooker.repository.room.SeatRepository;
import com.alicjawozniak.ticketbooker.repository.screening.ScreeningRepository;
import com.alicjawozniak.ticketbooker.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer {

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    private final SeatRepository seatRepository;

    private final TicketRepository ticketRepository;

    private final ScreeningRepository screeningRepository;

    private Random random = new Random();

    @PostConstruct
    public void initialize() {
        List<Movie> movies = Stream.of("Cats", "Dogs", "Savannah", "Forest", "Ocean")
                .map(this::createMovie)
                .collect(Collectors.toList());
        List<Room> rooms = Stream.of("Room 1", "Room 2", "Room 3", "Room 4", "Room 5")
                .map(this::createRoom)
                .collect(Collectors.toList());
        List<String> userSurnames = Arrays.asList("Kowalski", "Nowak", "Smith");

        List<Screening> screenings = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> createScreening(
                        getRandomElement(movies),
                        getRandomElement(rooms)
                ))
                .collect(Collectors.toList());

        IntStream.rangeClosed(1, 20)
                .forEach(i -> {
                    Screening screening = getRandomElement(screenings);
                    createTicket(
                            screening,
                            getRandomElement(userSurnames),
                            getRandomElement(screening.getFreeSeats())
                    );
                });
    }

    private Screening createScreening(Movie movie, Room room){
        int day = random.nextInt(3);
        int hour = random.nextInt(22);
        int minutes = random.nextInt(59);
        return screeningRepository.save(
                Screening.builder()
                        .movie(movie)
                        .room(room)
                        .startTime(LocalDateTime.now().plusDays(day).withHour(hour).withMinute(minutes))
                        .endTime(LocalDateTime.now().plusDays(day).withHour(hour + 2).withMinute(minutes))
                        .build()
        );
    }

    private Movie createMovie(String title){
        return movieRepository.save(
                Movie.builder()
                        .title(title)
                        .build()
        );
    }

    private Room createRoom(String number){
        return roomRepository.save(
                Room.builder()
                        .number(number)
                        .seats(createSeats())
                        .build()
        );
    }

    private List<Seat> createSeats(){
        return seatRepository.saveAll(
                IntStream.rangeClosed(1, 10)
                        .mapToObj(number -> Seat.builder()
                                .number("Seat " + number)
                                .build()
                        )
                        .collect(Collectors.toList())
        );
    }

    private void createTicket(Screening screening, String userSurname, Seat seat){
        ticketRepository.save(
                Ticket.builder()
                        .screening(screening)
                        .userName("Adam")
                        .userSurname(userSurname)
                        .typeQuantities(Collections.singletonMap(TicketType.ADULT, 1))
                        .seats(Collections.singletonList(seat))
                        .build()
        );
    }

    private <T> T getRandomElement(List<T> list) {
        int size = list.size();
        int randomIndex = random.nextInt(size-1);
        return list.get(randomIndex);
    }
}
