package com.alicjawozniak.ticketbooker.user;

import com.alicjawozniak.ticketbooker.domain.user.User;
import com.alicjawozniak.ticketbooker.dto.user.CreateUserDto;
import com.alicjawozniak.ticketbooker.dto.user.UserDto;
import com.alicjawozniak.ticketbooker.dto.user.UpdateUserDto;
import com.alicjawozniak.ticketbooker.repository.user.UserRepository;
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
public class UserCrudIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateUser() throws Exception {
        //given
        CreateUserDto createDto = CreateUserDto.builder()
                .name("Name 1")
                .surname("Surname 1")
                .build();

        //when
        final MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        UserDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getName()).isEqualTo(createDto.getName());
        assertThat(responseDto.getSurname()).isEqualTo(createDto.getSurname());

    }

    @Test
    public void canReadUser() throws Exception {
        //given
        User user = userRepository.save(
                User.builder()
                        .name("Name 1")
                        .surname("Surname 1")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(get("/users/{id}", user.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        UserDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getName()).isEqualTo(user.getName());
        assertThat(responseDto.getSurname()).isEqualTo(user.getSurname());
    }

    @Test
    public void canUpdateUser() throws Exception {
        //given
        User user = userRepository.save(
                User.builder()
                        .name("Name 1")
                        .surname("Surname 1")
                        .build()
        );

        UpdateUserDto updateDto = UpdateUserDto.builder()
                .name("Name 2")
                .surname("Surname 2")
                .build();

        //when
        final MvcResult result = mockMvc.perform(put("/users/{id}", user.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
        )
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        UserDto responseDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getName()).isEqualTo(updateDto.getName());
        assertThat(responseDto.getSurname()).isEqualTo(updateDto.getSurname());
    }

    @Test
    public void canDeleteUser() throws Exception {
        //given
        User user = userRepository.save(
                User.builder()
                        .name("Name 1")
                        .surname("Surname 1")
                        .build()
        );

        //when
        final MvcResult result = mockMvc.perform(delete("/users/{id}", user.getId().toString()))
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(userRepository.findAll()).isEmpty();
    }

}
