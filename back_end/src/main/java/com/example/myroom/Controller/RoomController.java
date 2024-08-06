//package com.example.myroom.Controller;
//
//import com.example.myroom.Entities.Juntion;
//import com.example.myroom.Entities.Room;
//import com.example.myroom.Entities.User;
//import com.example.myroom.Repository.JuntionRepository;
//import com.example.myroom.Repository.RoomRepository;
//import com.example.myroom.Repository.UserRepository;
//import com.example.myroom.Util.AES;
//import com.example.myroom.Util.SendEmail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@RestController
//@RequestMapping(path = "/room")
//public class RoomController {
//
//    @Autowired
//    RoomRepository roomRepository;
//
//    @Autowired
//    JuntionRepository juntionRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @GetMapping(path = "/listAll")
//    public ResponseEntity<?> listAll(String userId) throws Exception {
//        List<Room> listRoom = new ArrayList<>();
//        List<Juntion> listJun = juntionRepository.findAllByUserId(Integer.parseInt(AES.decrypt(userId, "KEMIATHOIKEMIATH")));
//
//        if(listJun != null){
//            for(Juntion juntion:listJun){
//                listRoom.add(roomRepository.findById(juntion.getRoomId()).get());
//            }
//            return ResponseEntity.ok(listRoom);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
//    }
//
//    @PostMapping(path = "/create")
//    public ResponseEntity<?> createRoom(String id, String name) throws Exception {
//        Random random = new Random();
//        Integer userId = Integer.parseInt(AES.decrypt(id, "KEMIATHOIKEMIATH"));
//        User user = userRepository.findById(userId).get();
//        int randomNumber = random.nextInt(900000) + 100000;
//
//        Room room = new Room();
//        room.setName(name);
//        room.setCreatedDate(LocalDate.now());
//        while (roomRepository.findRoomByCode(Integer.toString(randomNumber)) != null){
//            random = new Random();
//            randomNumber = random.nextInt(900000) + 100000;
//        }
//
//        room.setCode(Integer.toString(randomNumber));
//
//        String content = "<h1>Room code</h1><p>Your room code: <strong>" + randomNumber + "</strong></p>";
//        String response = SendEmail.send(user.getUserName(),"Mar Heaven sends your room code", content);
//
//        if(response.equals("OK")){
//            roomRepository.save(room);
//            Juntion juntion = new Juntion();
//            juntion.setUserId(userId);
//            juntion.setRoomId(room.getId());
//            juntionRepository.save(juntion);
//
//            return ResponseEntity.ok("CREATED");
//        }
//        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("NOT FOUND");
//    }
//
//    @PostMapping(path = "/add_by_code")
//    public ResponseEntity<?> addByCode(String userId, String code) throws Exception {
//        Room room = roomRepository.findRoomByCode(code);
//        if(room != null){
//            Juntion juntion = new Juntion();
//            juntion.setUserId(Integer.parseInt(AES.decrypt(userId, "KEMIATHOIKEMIATH")));
//            juntion.setRoomId(room.getId());
//            juntionRepository.save(juntion);
//            return ResponseEntity.ok("ADDED");
//        }
//        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("NOT FOUND");
//
//    }
//
//}
