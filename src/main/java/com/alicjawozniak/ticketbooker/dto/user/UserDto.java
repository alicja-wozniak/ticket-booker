package com.alicjawozniak.ticketbooker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;

    private String name;

    private String surname;

}
