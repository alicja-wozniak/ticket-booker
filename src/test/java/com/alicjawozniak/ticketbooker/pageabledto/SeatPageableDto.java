package com.alicjawozniak.ticketbooker.pageabledto;

import com.alicjawozniak.ticketbooker.dto.room.SeatDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class SeatPageableDto {
    List<SeatDto> content = new ArrayList<>();

    public List<SeatDto> getContent() {
        return content;
    }
}
