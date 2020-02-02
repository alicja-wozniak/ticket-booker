package com.alicjawozniak.ticketbooker.movie;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.dto.movie.CreateMovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.MovieDto;
import com.alicjawozniak.ticketbooker.dto.movie.UpdateMovieDto;
import com.alicjawozniak.ticketbooker.pageabledto.MoviePageableDto;
import com.alicjawozniak.ticketbooker.repository.movie.MovieRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
public class MovieIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateMovie() throws Exception {
        //given
        CreateMovieDto createDto = CreateMovieDto.builder()
                .title("Movie 1")
                .build();

        //when
        final MvcResult result = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        MovieDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MovieDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getTitle()).isEqualTo(createDto.getTitle());

    }

    @Test
    public void canReadMovie() throws Exception {
        //given
        Movie movie = movieRepository.save(
                Movie.builder()
                        .title("Movie 1")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/movies/{id}", movie.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        MovieDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MovieDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getTitle()).isEqualTo(movie.getTitle());
    }

    @Test
    public void canReadAllMovies() throws Exception {
        //given
        Movie movie1 = movieRepository.save(
                Movie.builder()
                        .title("Movie 1")
                        .build()
        );
        Movie movie2 = movieRepository.save(
                Movie.builder()
                        .title("Movie 2")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/movies")
                .param("title", movie1.getTitle())
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        MoviePageableDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MoviePageableDto.class);
        assertThat(responseDto.getContent()).isNotEmpty();
        assertThat(responseDto.getContent()).hasSize(1);
        assertThat(responseDto.getContent().get(0).getId()).isEqualTo(movie1.getId());
        assertThat(responseDto.getContent().get(0).getTitle()).isEqualTo(movie1.getTitle());
    }

    @Test
    public void canUpdateMovie() throws Exception {
        //given
        Movie movie = movieRepository.save(
                Movie.builder()
                        .title("Movie 1")
                        .build()
        );

        UpdateMovieDto updateDto = UpdateMovieDto.builder()
                .title("Movie 2")
                .build();

        //when
        final MvcResult result = mockMvc.perform(put("/movies/{id}", movie.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        MovieDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MovieDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getTitle()).isEqualTo(updateDto.getTitle());
    }

    @Test
    public void canDeleteMovie() throws Exception {
        //given
        Movie movie = movieRepository.save(
                Movie.builder()
                        .title("Movie 1")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(delete("/movies/{id}", movie.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(movieRepository.findAll()).isEmpty();
    }

}
