package com.example.bookingsystem.repository;

import com.example.bookingsystem.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long id);
    void deleteById(Long id);
}