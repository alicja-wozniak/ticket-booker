package com.alicjawozniak.ticketbooker.pageabledto;

import com.alicjawozniak.ticketbooker.dto.ticket.TicketDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class TicketPageableDto {
    List<TicketDto> content = new ArrayList<>();

    public List<TicketDto> getContent() {
        return content;
    }
}
