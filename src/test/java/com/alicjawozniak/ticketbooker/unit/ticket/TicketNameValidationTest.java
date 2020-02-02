package com.alicjawozniak.ticketbooker.unit.ticket;

import com.alicjawozniak.ticketbooker.dto.ticket.CreateTicketDto;
import com.alicjawozniak.ticketbooker.dto.ticket.UpdateTicketDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class TicketNameValidationTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("getUserNames")
    public void canValidateUserNameForCreateDto(String input, boolean expected) {
        //given
        CreateTicketDto createTicketDto = CreateTicketDto.builder()
                .userName(input)
                .userSurname("Nowak")
                .build();

        //when
        Set<ConstraintViolation<CreateTicketDto>> result = validator.validate(createTicketDto);

        //then
        assertThat(result.isEmpty()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getUserNames")
    public void canValidateUserNameForUpdateDto(String input, boolean expected) {
        //given
        UpdateTicketDto updateTicketDto = UpdateTicketDto.builder()
                .userName(input)
                .userSurname("Nowak")
                .build();

        //when
        Set<ConstraintViolation<UpdateTicketDto>> result = validator.validate(updateTicketDto);

        //then
        assertThat(result.isEmpty()).isEqualTo(expected);
    }

    private static Stream<Arguments> getUserNames() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("A", false),
                Arguments.of("Adam", true),
                Arguments.of("Adaaaaaaaaaaam", true),
                Arguments.of("AAA", false)
        );
    }

    @ParameterizedTest
    @MethodSource("getUserSurnames")
    public void canValidateUserSurnameForCreateDto(String input, boolean expected) {
        //given
        CreateTicketDto createTicketDto = CreateTicketDto.builder()
                .userName("Adam")
                .userSurname(input)
                .build();

        //when
        Set<ConstraintViolation<CreateTicketDto>> result = validator.validate(createTicketDto);

        //then
        assertThat(result.isEmpty()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getUserSurnames")
    public void canValidateUserSurnameForUpdateDto(String input, boolean expected) {
        //given
        UpdateTicketDto updateTicketDto = UpdateTicketDto.builder()
                .userName("Adam")
                .userSurname(input)
                .build();

        //when
        Set<ConstraintViolation<UpdateTicketDto>> result = validator.validate(updateTicketDto);

        //then
        assertThat(result.isEmpty()).isEqualTo(expected);
    }

    private static Stream<Arguments> getUserSurnames() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("S", false),
                Arguments.of("Smith", true),
                Arguments.of("Smiiiiiiiiiiiiith", true),
                Arguments.of("Smith-Kowalski", true),
                Arguments.of("Smith-kowalski", false),
                Arguments.of("smith-Kowalski", false),
                Arguments.of("smith-KKKKKK", false),
                Arguments.of("SSS", false)
        );
    }

}
