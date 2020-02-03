package com.alicjawozniak.ticketbooker.exception.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SeatTakenException extends RuntimeException {
    private List<Long> takenSeatIds;
}
