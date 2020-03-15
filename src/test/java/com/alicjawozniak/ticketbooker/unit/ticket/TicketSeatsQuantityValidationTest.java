package com.alicjawozniak.ticketbooker.unit.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class TicketSeatsQuantityValidationTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("getArguments")
    public void canValidateSeatQuantityForCreateDto(Map<TicketType,Integer> typeQuantities, List<Long> seatIds, boolean expected) {
        //given
        CreateTicketDto createTicketDto = CreateTicketDto.builder()
                .userName("Adam")
                .userSurname("Smith")
                .typeQuantities(typeQuantities)
                .seatIds(seatIds)
                .build();

        //when
        Set<ConstraintViolation<CreateTicketDto>> result = validator.validate(createTicketDto);

        //then
        assertThat(result.isEmpty()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    public void canValidateSeatQuantityForUpdateDto(Map<TicketType,Integer> typeQuantities, List<Long> seatIds, boolean expected) {
        //given
        UpdateTicketDto updateTicketDto = UpdateTicketDto.builder()
                .userName("Adam")
                .userSurname("Smith")
                .typeQuantities(typeQuantities)
                .seatIds(seatIds)
                .build();

        //when
        Set<ConstraintViolation<UpdateTicketDto>> result = validator.validate(updateTicketDto);

        //then
        assertThat(result.isEmpty()).isEqualTo(expected);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(Collections.emptyMap(), getSeatIds(1), false),
                Arguments.of(Map.of(TicketType.ADULT, 1), Collections.emptyList(), false),
                Arguments.of(Map.of(TicketType.ADULT, 1), getSeatIds(1), true),
                Arguments.of(Map.of(TicketType.ADULT, 3), getSeatIds(3), true),
                Arguments.of(Map.of(TicketType.ADULT, 5), getSeatIds(3), false),
                Arguments.of(Map.of(TicketType.ADULT, 3), getSeatIds(5), false),
                Arguments.of(Map.of(TicketType.ADULT, 1, TicketType.STUDENT, 1, TicketType.CHILD, 1), getSeatIds(3), true),
                Arguments.of(Map.of(TicketType.ADULT, 2, TicketType.STUDENT, 3, TicketType.CHILD, 4), getSeatIds(9), true)
        );
    }

    private static List<Long> getSeatIds(int quantity) {
        return LongStream.range(0, quantity)
                .boxed()
                .collect(Collectors.toList());
    }


}
