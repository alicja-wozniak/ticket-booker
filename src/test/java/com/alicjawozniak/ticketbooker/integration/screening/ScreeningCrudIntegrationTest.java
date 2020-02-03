package com.alicjawozniak.ticketbooker.integration.screening;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.CreateScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.UpdateScreeningDto;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
public class ScreeningCrudIntegrationTest {

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
                .content(objectMapper.writeValueAsString(createDto))
        )
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
        assertThat(responseDto.getFreeSeats()).hasSize(room.getSeats().size());
        assertThat(responseDto.getStartTime()).isEqualTo(createDto.getStartTime());
        assertThat(responseDto.getEndTime()).isEqualTo(createDto.getEndTime());

    }

    @Test
    public void canReadScreening() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = screeningRepository.save(
                Screening.builder()
                        .movie(movie)
                        .room(room)
                        .startTime(LocalDateTime.now().plusDays(1))
                        .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/screenings/{id}", screening.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        ScreeningDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), ScreeningDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getMovie()).isNotNull();
        assertThat(responseDto.getMovie().getId()).isEqualTo(movie.getId());
        assertThat(responseDto.getRoom()).isNotNull();
        assertThat(responseDto.getRoom().getId()).isEqualTo(room.getId());
        assertThat(responseDto.getFreeSeats()).hasSize(3);
        assertThat(responseDto.getStartTime()).isEqualTo(screening.getStartTime());
        assertThat(responseDto.getEndTime()).isEqualTo(screening.getEndTime());
    }

    @Test
    public void canUpdateScreening() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = screeningRepository.save(
                Screening.builder()
                        .movie(movie)
                        .room(room)
                        .startTime(LocalDateTime.now().plusDays(1))
                        .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .build()
        );

        Movie movie2 = createMovie();
        Room room2 = createRoom();
        UpdateScreeningDto updateDto = UpdateScreeningDto.builder()
                .movieId(movie2.getId())
                .roomId(room2.getId())
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                .build();

        //when
        final MvcResult result = mockMvc.perform(put("/screenings/{id}", screening.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        ScreeningDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), ScreeningDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getMovie()).isNotNull();
        assertThat(responseDto.getMovie().getId()).isEqualTo(updateDto.getMovieId());
        assertThat(responseDto.getRoom()).isNotNull();
        assertThat(responseDto.getRoom().getId()).isEqualTo(updateDto.getRoomId());
        assertThat(responseDto.getFreeSeats()).hasSize(room2.getSeats().size());
        assertThat(responseDto.getStartTime()).isEqualTo(updateDto.getStartTime());
        assertThat(responseDto.getEndTime()).isEqualTo(updateDto.getEndTime());
    }

    @Test
    public void canDeleteScreening() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = screeningRepository.save(
                Screening.builder()
                        .movie(movie)
                        .room(room)
                        .startTime(LocalDateTime.now().plusDays(1))
                        .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(delete("/screenings/{id}", screening.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(screeningRepository.findAll()).isEmpty();
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
                                .number(1L)
                                .build(),
                        Seat.builder()
                                .number(2L)
                                .build(),
                        Seat.builder()
                                .number(3L)
                                .build()
                )
        );
    }
}
