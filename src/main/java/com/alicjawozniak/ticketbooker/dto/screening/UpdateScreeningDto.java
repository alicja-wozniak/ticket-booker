package com.alicjawozniak.ticketbooker.dto.screening;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateScreeningDto {

    private Long movieId;

    private Long roomId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
