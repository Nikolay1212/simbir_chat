package com.simbir.chat.repository;

import com.simbir.chat.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {
    @Query("select r from Room r where r.roomType='DEFAULT'")
    List<Room> findByAccessLevel();
}
