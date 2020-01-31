package com.alicjawozniak.ticketbooker.dto.screening.grouped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupedScreeningWrapperDto {

    private List<SingleGroupedScreeningDto> groupedScreenings;
}
