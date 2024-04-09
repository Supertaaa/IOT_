package com.example.myroom.Repository;

import com.example.myroom.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    public Room findRoomByCode(String code);
}
