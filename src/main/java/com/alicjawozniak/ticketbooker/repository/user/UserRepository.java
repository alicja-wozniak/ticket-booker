package com.alicjawozniak.ticketbooker.repository.user;

import com.alicjawozniak.ticketbooker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
