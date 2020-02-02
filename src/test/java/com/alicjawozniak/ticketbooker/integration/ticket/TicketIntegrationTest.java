package com.alicjawozniak.ticketbooker.integration.ticket;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.domain.ticket.Ticket;
import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import com.alicjawozniak.ticketbooker.pageabledto.TicketPageableDto;
import com.alicjawozniak.ticketbooker.repository.movie.MovieRepository;
import com.alicjawozniak.ticketbooker.repository.room.RoomRepository;
import com.alicjawozniak.ticketbooker.repository.room.SeatRepository;
import com.alicjawozniak.ticketbooker.repository.screening.ScreeningRepository;
import com.alicjawozniak.ticketbooker.repository.ticket.TicketRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class TicketIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateTicket() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = createScreening(movie, room);
        CreateTicketDto createDto = CreateTicketDto.builder()
                .type(TicketType.ADULT)
                .userName("Adam")
                .userSurname("Smith")
                .screeningId(screening.getId())
                .seatIds(Collections.singletonList(room.getSeats().get(0).getId()))
                .build();

        //when
        final MvcResult result = mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        TicketDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), TicketDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getType()).isEqualTo(createDto.getType());
        assertThat(responseDto.getUserName()).isEqualTo(createDto.getUserName());
        assertThat(responseDto.getUserSurname()).isEqualTo(createDto.getUserSurname());
        assertThat(responseDto.getSeats()).isNotNull();
        assertThat(responseDto.getSeats()).isNotEmpty();
        assertThat(responseDto.getSeats().get(0).getId()).isEqualTo(createDto.getSeatIds().get(0));
        assertThat(responseDto.getScreening()).isNotNull();
        assertThat(responseDto.getScreening().getId()).isEqualTo(createDto.getScreeningId());

    }

    @Test
    public void canReadTicket() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = createScreening(movie, room);
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                        .type(TicketType.ADULT)
                        .userName("Adam")
                        .userSurname("Smith")
                        .seats(Collections.singletonList(room.getSeats().get(0)))
                        .screening(screening)
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/tickets/{id}", ticket.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        TicketDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), TicketDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getType()).isEqualTo(ticket.getType());
        assertThat(responseDto.getUserName()).isEqualTo(ticket.getUserName());
        assertThat(responseDto.getUserSurname()).isEqualTo(ticket.getUserSurname());
        assertThat(responseDto.getSeats()).isNotNull();
        assertThat(responseDto.getSeats()).isNotEmpty();
        assertThat(responseDto.getSeats().get(0).getId()).isEqualTo(ticket.getSeats().get(0).getId());
        assertThat(responseDto.getScreening()).isNotNull();
        assertThat(responseDto.getScreening().getId()).isEqualTo(screening.getId());
    }

    @Test
    public void canReadAllTickets() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = createScreening(movie, room);
        Ticket ticket1 = ticketRepository.save(
                Ticket.builder()
                        .type(TicketType.ADULT)
                        .userName("Adam")
                        .userSurname("Smith")
                        .seats(Collections.singletonList(room.getSeats().get(0)))
                        .screening(screening)
                        .build()
        );
        Ticket ticket2 = ticketRepository.save(
                Ticket.builder()
                        .type(TicketType.ADULT)
                        .userName("John")
                        .userSurname("Nowak")
                        .seats(Collections.singletonList(room.getSeats().get(0)))
                        .screening(screening)
                        .build()
        );


        //when
        final MvcResult result = mockMvc.perform(get("/tickets")
                .param("userSurname", ticket1.getUserSurname())
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        TicketPageableDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), TicketPageableDto.class);
        assertThat(responseDto.getContent()).isNotEmpty();
        assertThat(responseDto.getContent()).hasSize(1);
        assertThat(responseDto.getContent().get(0).getId()).isEqualTo(ticket1.getId());
        assertThat(responseDto.getContent().get(0).getType()).isEqualTo(ticket1.getType());
        assertThat(responseDto.getContent().get(0).getUserName()).isEqualTo(ticket1.getUserName());
        assertThat(responseDto.getContent().get(0).getUserSurname()).isEqualTo(ticket1.getUserSurname());
        assertThat(responseDto.getContent().get(0).getSeats()).isNotNull();
        assertThat(responseDto.getContent().get(0).getSeats()).isNotEmpty();
        assertThat(responseDto.getContent().get(0).getSeats().get(0).getId()).isEqualTo(ticket1.getSeats().get(0).getId());
        assertThat(responseDto.getContent().get(0).getScreening()).isNotNull();
        assertThat(responseDto.getContent().get(0).getScreening().getId()).isEqualTo(screening.getId());    }

    @Test
    public void canUpdateTicket() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = createScreening(movie, room);
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                        .type(TicketType.ADULT)
                        .userName("Adam")
                        .userSurname("Smith")
                        .seats(new ArrayList<>(List.of(room.getSeats().get(0))))
                        .screening(screening)
                        .build()
        );

        Movie movie2 = createMovie();
        Room room2 = createRoom();
        Screening screening2 = createScreening(movie2, room2);
        UpdateTicketDto updateDto = UpdateTicketDto.builder()
                .type(TicketType.STUDENT)
                .userName("John")
                .userSurname("Nowak")
                .screeningId(screening2.getId())
                .seatIds(new ArrayList<>(List.of(room2.getSeats().get(0).getId())))
                .build();

        //when
        final MvcResult result = mockMvc.perform(put("/tickets/{id}", ticket.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        TicketDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), TicketDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getType()).isEqualTo(updateDto.getType());
        assertThat(responseDto.getUserName()).isEqualTo(updateDto.getUserName());
        assertThat(responseDto.getUserSurname()).isEqualTo(updateDto.getUserSurname());
        assertThat(responseDto.getSeats()).isNotNull();
        assertThat(responseDto.getSeats()).isNotEmpty();
        assertThat(responseDto.getSeats().get(0).getId()).isEqualTo(updateDto.getSeatIds().get(0));
        assertThat(responseDto.getScreening()).isNotNull();
        assertThat(responseDto.getScreening().getId()).isEqualTo(updateDto.getScreeningId());
    }

    @Test
    public void canDeleteTicket() throws Exception {
        //given
        Movie movie = createMovie();
        Room room = createRoom();
        Screening screening = createScreening(movie, room);
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                        .type(TicketType.ADULT)
                        .userName("Adam")
                        .userSurname("Smith")
                        .seats(Collections.singletonList(room.getSeats().get(0)))
                        .screening(screening)
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(delete("/tickets/{id}", ticket.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(ticketRepository.findAll()).isEmpty();
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

    private Movie createMovie(){
        return movieRepository.save(
                Movie.builder()
                        .title("Movie 1")
                        .build()
        );
    }

    private Screening createScreening(Movie movie, Room room){
        return screeningRepository.save(
                Screening.builder()
                        .movie(movie)
                        .room(room)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(12))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(14))
                        .build()
        );
    }
}
