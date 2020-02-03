package com.alicjawozniak.ticketbooker.exception.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SeatTakenException extends RuntimeException {
    private List<Long> takenSeatIds;
}
