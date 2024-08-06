package com.example.myroom.Service;

import org.springframework.http.ResponseEntity;

public interface RoomInterface {
    ResponseEntity<?> listAll(String username);
    ResponseEntity<?> createRoom(String username, String roomName);
}
