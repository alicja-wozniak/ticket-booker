package com.alicjawozniak.ticketbooker.room;

import com.alicjawozniak.ticketbooker.domain.movie.Movie;
import com.alicjawozniak.ticketbooker.domain.room.Room;
import com.alicjawozniak.ticketbooker.dto.room.CreateRoomDto;
import com.alicjawozniak.ticketbooker.dto.room.RoomDto;
import com.alicjawozniak.ticketbooker.dto.room.UpdateRoomDto;
import com.alicjawozniak.ticketbooker.pageabledto.MoviePageableDto;
import com.alicjawozniak.ticketbooker.pageabledto.RoomPageableDto;
import com.alicjawozniak.ticketbooker.repository.room.RoomRepository;
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
public class RoomIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateRoom() throws Exception {
        //given
        CreateRoomDto createDto = CreateRoomDto.builder()
                .number("Room 1")
                .build();

        //when
        final MvcResult result = mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        RoomDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), RoomDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getNumber()).isEqualTo(createDto.getNumber());

    }

    @Test
    public void canReadRoom() throws Exception {
        //given
        Room room = roomRepository.save(
                Room.builder()
                        .number("Room 1")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/rooms/{id}", room.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        RoomDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), RoomDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getNumber()).isEqualTo(room.getNumber());
    }

    @Test
    public void canReadAllRooms() throws Exception {
        //given
        Room room1 = roomRepository.save(
                Room.builder()
                        .number("Room 1")
                        .build()
        );
        Room room2 = roomRepository.save(
                Room.builder()
                        .number("Room 2")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/rooms"))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        RoomPageableDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), RoomPageableDto.class);
        assertThat(responseDto.getContent()).isNotEmpty();
        assertThat(responseDto.getContent()).hasSize(2);
        assertThat(responseDto.getContent()).anyMatch(
                roomDto -> roomDto.getId().equals(room1.getId())
                && roomDto.getNumber().equals(room1.getNumber())
        );
        assertThat(responseDto.getContent()).anyMatch(
                roomDto -> roomDto.getId().equals(room2.getId())
                        && roomDto.getNumber().equals(room2.getNumber())
        );
    }

    @Test
    public void canUpdateRoom() throws Exception {
        //given
        Room room = roomRepository.save(
                Room.builder()
                        .number("Room 1")
                        .build()
        );

        UpdateRoomDto updateDto = UpdateRoomDto.builder()
                .number("Room 2")
                .build();

        //when
        final MvcResult result = mockMvc.perform(put("/rooms/{id}", room.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        RoomDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), RoomDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getNumber()).isEqualTo(updateDto.getNumber());
    }

    @Test
    public void canDeleteRoom() throws Exception {
        //given
        Room room = roomRepository.save(
                Room.builder()
                        .number("Room 1")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(delete("/rooms/{id}", room.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(roomRepository.findAll()).isEmpty();
    }

}
