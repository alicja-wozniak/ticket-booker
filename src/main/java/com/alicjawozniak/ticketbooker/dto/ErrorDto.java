package com.alicjawozniak.ticketbooker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorDto {

    private HttpStatus status;

    private LocalDateTime timestamp;

    private String message;

}
