package com.alicjawozniak.ticketbooker.integration.screening;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.alicjawozniak.ticketbooker.dto.screening.grouped.GroupedScreeningWrapperDto;
import com.alicjawozniak.ticketbooker.dto.screening.grouped.SingleGroupedScreeningDto;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
public class ScreeningReadAllIntegrationTest {

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
    public void canReadAllScreenings() throws Exception {
        //given
        List<Screening> screenings = createScreenings();

        //when
        final MvcResult result = mockMvc.perform(get("/screenings"))
                .andReturn();

        //then
        ScreeningPageableDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), ScreeningPageableDto.class);
        assertThat(responseDto.getContent()).isNotEmpty();
        assertThat(responseDto.getContent()).hasSameSizeAs(screenings);
        assertEqualByElementsFields(screenings, responseDto.getContent());
    }

    @Test
    public void canReadAllScreeningsWithFilters() throws Exception {
        //given
        List<Screening> screenings = createScreenings();
        Movie movie = screenings.get(0).getMovie();
        List<Screening> screeningsToBeFound = screenings
                .stream()
                .filter(screening -> screening.getMovie().equals(movie))
                .collect(Collectors.toList());

        //when
        final MvcResult result = mockMvc.perform(get("/screenings")
                .param("minStartTime", LocalDateTime.now().plusDays(1).withHour(0).toString())
                .param("maxStartTime", LocalDateTime.now().plusDays(1).withHour(23).toString())
                .param("movieId", screenings.get(0).getMovie().getId().toString())
        )
                .andReturn();

        //then
        ScreeningPageableDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), ScreeningPageableDto.class);
        assertThat(responseDto.getContent()).isNotEmpty();
        assertThat(responseDto.getContent()).hasSameSizeAs(screeningsToBeFound);
        screeningsToBeFound
                .forEach(
                        screening -> assertThat(responseDto.getContent()).anyMatch(
                                responseScreening -> areEqualByFields(screening, responseScreening)
                        )
                );
    }

    @Test
    public void canReadAllScreeningsGroupedByMovie() throws Exception {
        //given
        List<Screening> screenings = createScreenings()
                .stream()
                .sorted(Comparator.comparingLong(Screening::getId))
                .collect(Collectors.toList());

        //when
        final MvcResult result = mockMvc.perform(get("/screenings/grouped-by-movie")
                .param("minStartTime", LocalDateTime.now().plusDays(1).withHour(0).toString())
                .param("maxStartTime", LocalDateTime.now().plusDays(1).withHour(23).toString())
                .param("sort", "id,ASC")
        )
                .andReturn();

        //then
        GroupedScreeningWrapperDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), GroupedScreeningWrapperDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getGroupedScreenings()).hasSize(2);
        List<SingleGroupedScreeningDto> sortedScreeningDtos = responseDto.getGroupedScreenings()
                .stream().sorted(Comparator.comparingLong(dto -> dto.getMovieDto().getId())).collect(Collectors.toList());

        assertThat(sortedScreeningDtos.get(0).getMovieDto().getId()).isEqualTo(screenings.get(0).getMovie().getId());
        assertThat(sortedScreeningDtos.get(0).getScreenings()).hasSize(2);
        assertThat(areEqualByFields(screenings.get(0), sortedScreeningDtos.get(0).getScreenings().get(0))).isTrue();
        assertThat(areEqualByFields(screenings.get(2), sortedScreeningDtos.get(0).getScreenings().get(1))).isTrue();

        assertThat(sortedScreeningDtos.get(1).getMovieDto().getId()).isEqualTo(screenings.get(1).getMovie().getId());
        assertThat(sortedScreeningDtos.get(1).getScreenings()).hasSize(1);
        assertThat(areEqualByFields(screenings.get(1), sortedScreeningDtos.get(1).getScreenings().get(0))).isTrue();
    }

    private List<Screening> createScreenings() {
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();
        Room room1 = createRoom();
        Room room2 = createRoom();
        Screening screening1 = screeningRepository.save(
                Screening.builder()
                        .movie(movie1)
                        .room(room1)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(10))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(12))
                        .build()
        );

        Screening screening2 = screeningRepository.save(
                Screening.builder()
                        .movie(movie2)
                        .room(room1)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(11))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(14))
                        .build()
        );

        Screening screening3 = screeningRepository.save(
                Screening.builder()
                        .movie(movie1)
                        .room(room2)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(18))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(20))
                        .build()
        );

        return Arrays.asList(screening1, screening2, screening3);
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

    private void assertEqualByElementsFields(List<Screening> screenings, List<ScreeningDto> screeningDtos) {
        screenings
                .forEach(
                        screening -> assertThat(screeningDtos).anyMatch(
                                responseScreening -> areEqualByFields(screening, responseScreening)
                        )
                );
    }

    private boolean areEqualByFields(Screening screening, ScreeningDto screeningDto) {
        return screening.getId().equals(screeningDto.getId())
                && screening.getMovie().getId().equals(screeningDto.getMovie().getId())
                && screening.getRoom().getId().equals(screeningDto.getRoom().getId())
                && screening.getStartTime().equals(screeningDto.getStartTime())
                && screening.getEndTime().equals(screeningDto.getEndTime());
    }

}
