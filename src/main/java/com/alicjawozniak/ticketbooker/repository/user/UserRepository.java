package com.alicjawozniak.ticketbooker.repository.user;

import com.alicjawozniak.ticketbooker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {
}
