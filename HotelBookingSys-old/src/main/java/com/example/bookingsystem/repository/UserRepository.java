package com.example.bookingsystem.repository;

import com.example.bookingsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void deleteByUsername(String username);
}
