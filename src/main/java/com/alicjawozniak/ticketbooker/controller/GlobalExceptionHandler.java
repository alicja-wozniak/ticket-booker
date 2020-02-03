package com.alicjawozniak.ticketbooker.controller;

import com.alicjawozniak.ticketbooker.dto.ErrorDto;
import com.alicjawozniak.ticketbooker.exception.ticket.SeatTakenException;
import com.alicjawozniak.ticketbooker.exception.ticket.TooLateReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(final Exception e, final WebRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                ErrorDto.builder()
                .message("Exception")
                .status(status)
                .timestamp(LocalDateTime.now())
                .build(),
                status);
    }

    @ExceptionHandler(TooLateReservationException.class)
    public ResponseEntity<ErrorDto> handleTooLateReservationException(final TooLateReservationException e, final WebRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .message("Too late reservation")
                        .status(status)
                        .timestamp(LocalDateTime.now())
                        .params(null)
                        .build(),
                status);
    }

    @ExceptionHandler(SeatTakenException.class)
    public ResponseEntity<ErrorDto> handleSeatTakenException(final SeatTakenException e, final WebRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .message("Seat already taken")
                        .status(status)
                        .timestamp(LocalDateTime.now())
                        .params(toParams(e.getTakenSeatIds()))
                        .build(),
                status);
    }

    private List<String> toParams(List<Long> longs) {
        return longs.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

}