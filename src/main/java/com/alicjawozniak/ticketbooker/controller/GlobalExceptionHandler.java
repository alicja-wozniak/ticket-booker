package com.alicjawozniak.ticketbooker.controller;

import com.alicjawozniak.ticketbooker.dto.ErrorDto;
import com.alicjawozniak.ticketbooker.exception.ticket.TooLateReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

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
                        .build(),
                status);
    }

}