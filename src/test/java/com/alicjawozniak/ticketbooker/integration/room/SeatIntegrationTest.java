package com.alicjawozniak.ticketbooker.integration.room;

import com.alicjawozniak.ticketbooker.domain.room.Seat;
import com.alicjawozniak.ticketbooker.dto.room.CreateSeatDto;
import com.alicjawozniak.ticketbooker.dto.room.SeatDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateSeatDto;
import com.alicjawozniak.ticketbooker.repository.room.SeatRepository;
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
public class SeatIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateSeat() throws Exception {
        //given
        CreateSeatDto createDto = CreateSeatDto.builder()
                .number(1L)
                .build();

        //when
        final MvcResult result = mockMvc.perform(post("/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        SeatDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), SeatDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getNumber()).isEqualTo(createDto.getNumber());

    }

    @Test
    public void canReadSeat() throws Exception {
        //given
        Seat seat = seatRepository.save(
                Seat.builder()
                        .number(1L)
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/seats/{id}", seat.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        SeatDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), SeatDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getNumber()).isEqualTo(seat.getNumber());
    }

    @Test
    public void canUpdateSeat() throws Exception {
        //given
        Seat seat = seatRepository.save(
                Seat.builder()
                        .number(1L)
                        .build()
        );

        UpdateSeatDto updateDto = UpdateSeatDto.builder()
                .number(2L)
                .build();

        //when
        final MvcResult result = mockMvc.perform(put("/seats/{id}", seat.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        SeatDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), SeatDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getNumber()).isEqualTo(updateDto.getNumber());
    }

    @Test
    public void canDeleteSeat() throws Exception {
        //given
        Seat seat = seatRepository.save(
                Seat.builder()
                        .number(1L)
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(delete("/seats/{id}", seat.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(seatRepository.findAll()).isEmpty();
    }

}
