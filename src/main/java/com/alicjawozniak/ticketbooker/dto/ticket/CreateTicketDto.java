package com.alicjawozniak.ticketbooker.dto.ticket;

import com.alicjawozniak.ticketbooker.domain.ticket.TicketType;
import com.alicjawozniak.ticketbooker.dto.validation.ticket.ValidSeatsQuantity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ValidSeatsQuantity
public class CreateTicketDto implements SeatsQuantityDto {

    private Map<TicketType,Integer> typeQuantities;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]{2,}")
    private String userName;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]{2,}(-[A-Z][a-z]{2,})?")
    private String userSurname;

    private Long screeningId;

    private List<Long> seatIds;
}
