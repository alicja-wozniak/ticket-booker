package com.alicjawozniak.ticketbooker.controller.user;

import com.alicjawozniak.ticketbooker.domain.user.User;
import com.alicjawozniak.ticketbooker.dto.user.CreateUserDto;
import com.alicjawozniak.ticketbooker.dto.user.UserDto;
import com.alicjawozniak.ticketbooker.dto.user.UpdateUserDto;

public class UserDtoMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public static User toDomain(CreateUserDto dto) {
        return User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
    }

    public static User toDomain(UpdateUserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
    }
}
