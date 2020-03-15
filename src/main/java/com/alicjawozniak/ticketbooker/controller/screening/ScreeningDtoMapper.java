package com.alicjawozniak.ticketbooker.controller.screening;

import com.alicjawozniak.ticketbooker.controller.DtoMapperConfig;
import com.alicjawozniak.ticketbooker.domain.screening.Screening;
import com.alicjawozniak.ticketbooker.dto.screening.ScreeningDto;
import org.mapstruct.Mapper;

@Mapper(config = DtoMapperConfig.class)
public interface ScreeningDtoMapper {

    ScreeningDto toDto(Screening screening);

}
