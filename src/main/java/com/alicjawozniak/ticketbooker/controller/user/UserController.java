package com.alicjawozniak.ticketbooker.controller.user;

import com.alicjawozniak.ticketbooker.dto.user.CreateUserDto;
import com.alicjawozniak.ticketbooker.dto.user.UpdateUserDto;
import com.alicjawozniak.ticketbooker.dto.user.UserDto;
import com.alicjawozniak.ticketbooker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid CreateUserDto dto){
        return UserDtoMapper.toDto(
                userService.create(dto)
        );
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable("id") Long id){
        return UserDtoMapper.toDto(
                userService.read(id)
        );
    }

    @GetMapping
    public Page<UserDto> readAll(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return userService.readAll(pageable)
                .map(UserDtoMapper::toDto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UpdateUserDto dto){
        return UserDtoMapper.toDto(
                userService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }
}
