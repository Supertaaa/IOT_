package com.example.myroom.Service.Impl;

import com.example.myroom.Entities.Juntion;
import com.example.myroom.Entities.Room;
import com.example.myroom.Entities.User;
import com.example.myroom.Repository.JuntionRepository;
import com.example.myroom.Repository.RoomRepository;
import com.example.myroom.Repository.UserRepository;
import com.example.myroom.Service.RoomInterface;
import com.example.myroom.Util.AES;
import com.example.myroom.Util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


@Service
public class RoomImpl implements RoomInterface {

    @Autowired
    JuntionRepository juntionRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public HashMap<String, Object> listAll(Integer userId) throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        List<Room> listRoom = new ArrayList<>();
        List<Juntion> listJun = juntionRepository.findAllByUserId(userId);

        if(!listJun.isEmpty()){
            for(Juntion juntion:listJun){
                listRoom.add(roomRepository.findById(juntion.getRoomId()).get());
            }
            result.put("error_code", "0");
            result.put("data", listRoom);
            return result;
        }
        result.put("error_code", "-1");
        result.put("error_message", "NOT FOUND DEVICE");


        return result;
    }

    @Override
    public HashMap<String, Object> createRoom(Integer userId, String roomName) {

        HashMap<String, Object> result = new HashMap<>();
        Random random = new Random();

        User user = userRepository.findById(userId).get();
        int randomNumber = random.nextInt(900000) + 100000;

        Room room = new Room();
        room.setName(roomName);
        room.setCreatedDate(LocalDateTime.now());
        while (roomRepository.findRoomByCode(Integer.toString(randomNumber)) != null){
            random = new Random();
            randomNumber = random.nextInt(900000) + 100000;
        }
        room.setCode(Integer.toString(randomNumber));
        String content = "<h1>Room code</h1><p>Your room code: <strong>" + randomNumber + "</strong></p>";
        String response = SendEmail.send(user.getUserName(),"Mar Heaven sends your room code", content);

        if(response.equals("OK")){
            roomRepository.save(room);
            Juntion juntion = new Juntion();
            juntion.setUserId(userId);
            juntion.setRoomId(room.getId());
            juntion.setCreatedDate(LocalDateTime.now());

            juntionRepository.save(juntion);

            result.put("error_code", "0");

            return result;
        }
        result.put("error_code", "-1");
        result.put("error_message", "NOT FOUND");

        return result;
    }

    @Override
    public HashMap<String, Object> addByCode(Integer userId, String code) {
        HashMap<String, Object> result = new HashMap<>();
        Room room = roomRepository.findRoomByCode(code);
        if(room != null){
            Juntion juntion = new Juntion();
            juntion.setUserId(userId);
            juntion.setRoomId(room.getId());
            juntion.setCreatedDate(LocalDateTime.now());

            juntionRepository.save(juntion);
            result.put("error_code", "0");

            return result;
        }
        result.put("error_code", "-1");
        result.put("error_message", "NOT FOUND");

        return result;
    }
}
