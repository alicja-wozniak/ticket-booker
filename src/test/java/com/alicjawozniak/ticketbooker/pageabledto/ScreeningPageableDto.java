package com.alicjawozniak.ticketbooker.pageabledto;

import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningPageableDto {
    List<ScreeningDto> content = new ArrayList<>();

    public List<ScreeningDto> getContent() {
        return content;
    }
}
