package com.alicjawozniak.ticketbooker;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.CreateScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.alicjawozniak.ticketbooker.pageabledto.ScreeningPageableDto;
import com.alicjawozniak.ticketbooker.repository.movie.MovieRepository;
import com.alicjawozniak.ticketbooker.repository.room.RoomRepository;
import com.alicjawozniak.ticketbooker.repository.room.SeatRepository;
import com.alicjawozniak.ticketbooker.repository.screening.ScreeningRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class ScreeningIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateScreening() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        CreateScreeningDto createDto = CreateScreeningDto.builder()
                .movieId(movie.getId())
                .roomId(room.getId())
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                .build();

        //when
        final MvcResult result = mockMvc.perform(post("/screenings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        ScreeningDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), ScreeningDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getMovie()).isNotNull();
        assertThat(responseDto.getMovie().getId()).isEqualTo(createDto.getMovieId());
        assertThat(responseDto.getRoom()).isNotNull();
        assertThat(responseDto.getRoom().getId()).isEqualTo(createDto.getRoomId());
        assertThat(responseDto.getStartTime()).isEqualTo(createDto.getStartTime());
        assertThat(responseDto.getEndTime()).isEqualTo(createDto.getEndTime());

    }

    @Test
    public void canReadAllScreenings() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening1 = screeningRepository.save(
                Screening.builder()
                        .movie(movie)
                        .room(room)
                        .startTime(LocalDateTime.now().plusDays(1))
                        .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/screenings"))
                .andReturn();

        //then
        ScreeningPageableDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), ScreeningPageableDto.class);
        assertThat(responseDto.getContent()).isNotEmpty();
    }

    private Movie createMovie(){
        return movieRepository.save(
                Movie.builder()
                        .title("Movie 1")
                        .build()
        );
    }

    private Room createRoom(){
        return roomRepository.save(
                Room.builder()
                        .number("Room 1")
                        .seats(createSeats())
                        .build()
        );
    }

    private List<Seat> createSeats(){
        return seatRepository.saveAll(
                Arrays.asList(
                        Seat.builder()
                                .number("Seat 1")
                                .build(),
                        Seat.builder()
                                .number("Seat 2")
                                .build(),
                        Seat.builder()
                                .number("Seat 3")
                                .build()
                )
        );
    }
}
