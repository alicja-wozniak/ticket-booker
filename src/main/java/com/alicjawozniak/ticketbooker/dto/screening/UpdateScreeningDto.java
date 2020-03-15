package com.alicjawozniak.ticketbooker.dto.screening;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateScreeningDto {

    private Long movieId;

    private Long roomId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
