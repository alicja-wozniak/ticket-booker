package com.alicjawozniak.ticketbooker.pageabledto;

import com.alicjawozniak.ticketbooker.dto.room.RoomDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoomPageableDto {
    List<RoomDto> content = new ArrayList<>();

    public List<RoomDto> getContent() {
        return content;
    }
}
