package com.alicjawozniak.ticketbooker.service.user;

import com.alicjawozniak.ticketbooker.domain.user.User;
import com.alicjawozniak.ticketbooker.dto.user.CreateUserDto;
import com.alicjawozniak.ticketbooker.dto.user.UpdateUserDto;
import com.alicjawozniak.ticketbooker.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(CreateUserDto dto) {
        return userRepository.save(
                toDomain(dto)
        );
    }

    public User read(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<User> readAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User update(Long id, UpdateUserDto dto) {
        return userRepository.save(
                toDomain(id, dto)
        );
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User toDomain(CreateUserDto dto) {
        return User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
    }

    private User toDomain(Long id, UpdateUserDto dto) {
        return User.builder()
                .id(id)
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
    }
}
