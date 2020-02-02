package com.alicjawozniak.ticketbooker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateUserDto {

    private Long id;

    private String name;

    private String surname;

}
