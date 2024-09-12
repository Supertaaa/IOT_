package com.example.myroom.Service;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface RoomInterface {
    HashMap<String, Object> listAll(Integer userId) throws Exception;
    HashMap<String, Object> createRoom(Integer userId, String roomName);
    HashMap<String, Object> addByCode(Integer userId, String code);
}
