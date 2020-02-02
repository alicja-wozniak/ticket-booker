package com.alicjawozniak.ticketbooker.pageabledto;

import com.alicjawozniak.ticketbooker.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPageableDto {
    List<UserDto> content = new ArrayList<>();

    public List<UserDto> getContent() {
        return content;
    }
}
