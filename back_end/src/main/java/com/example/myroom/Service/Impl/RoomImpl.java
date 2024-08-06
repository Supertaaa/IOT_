package com.example.myroom.Service.Impl;

import com.example.myroom.Service.RoomInterface;
import org.springframework.http.ResponseEntity;

public class RoomImpl implements RoomInterface {
    @Override
    public ResponseEntity<?> listAll(String username) {
        return null;
    }

    @Override
    public ResponseEntity<?> createRoom(String username, String roomName) {
        return null;
    }
}
