//package com.example.myroom.Controller;
//
//
//import com.example.myroom.Entities.DeviceInDetail;
//import com.example.myroom.Entities.Juntion;
//import com.example.myroom.Entities.Room;
//import com.example.myroom.Repository.DeviceInDetailRepository;
//import com.example.myroom.Repository.DeviceTypeRepository;
//import com.example.myroom.Repository.JuntionRepository;
//import com.example.myroom.Repository.RoomRepository;
//import com.example.myroom.Util.AES;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping(path = "/detail")
//public class DetailController {
//
//    @Autowired
//    DeviceInDetailRepository deviceInDetailRepository;
//
//    @Autowired
//    DeviceTypeRepository deviceTypeRepository;
//
//    @Autowired
//    JuntionRepository juntionRepository;
//
//    @Autowired
//    RoomRepository roomRepository;
//
//    @GetMapping(path = "/listAllByRoom")
//    public ResponseEntity<?> getListDetail(String id) throws Exception {
//
//
//        return ResponseEntity.ok(deviceInDetailRepository.findAllByRoomId(id));
//    }
//
//    @GetMapping(path = "/listAll")
//    public ResponseEntity<?> listAll(String userId) throws Exception {
//
//        List<DeviceInDetail> listDevice = new ArrayList<>();
//        List<Juntion> listJun = juntionRepository.findAllByUserId(Integer.parseInt(AES.decrypt(userId, "KEMIATHOIKEMIATH")));
//
//        if(listJun != null){
//            for(Juntion juntion:listJun){
//                listDevice.addAll(deviceInDetailRepository.findAllByRoomId(Integer.toString(juntion.getRoomId())));
//            }
//            return ResponseEntity.ok(listDevice);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
//    }
//
//    @DeleteMapping(path = "/delete")
//    public ResponseEntity<?> deleteDetail(Integer id){
//        Optional<DeviceInDetail> device = deviceInDetailRepository.findById(id);
//        if(device.isPresent()){
//            deviceInDetailRepository.delete(device.get());
//            return ResponseEntity.ok("OK");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
//
//    }
//
//    @PostMapping(path = "/create")
//    public ResponseEntity<?> createDetail(DeviceInDetail inputDevice) throws Exception {
//
//
//        DeviceInDetail device = new DeviceInDetail();
//        device.setName(inputDevice.getName());
//        device.setDeviceType(inputDevice.getDeviceType());
//        device.setCreatedDate(LocalDate.now());
//        device.setPin(inputDevice.getPin());
//        device.setDeviceURL(inputDevice.getDeviceURL());
//        device.setDevicePort(inputDevice.getDevicePort());
//        device.setRoomId(inputDevice.getRoomId());
//        device.setStatus("OFF");
//
//        deviceInDetailRepository.save(device);
//        return ResponseEntity.ok("CREATED");
//    }
//
//    @PutMapping(path = "/update")
//    public ResponseEntity<?> updateDetail(DeviceInDetail newInfordevice){
//        Optional<DeviceInDetail> oldInforDevice = deviceInDetailRepository.findById(newInfordevice.getId());
//        if(oldInforDevice.isPresent()){
//            if(!newInfordevice.getName().isEmpty()){
//                oldInforDevice.get().setName(newInfordevice.getName());
//            }
//
//            if(!newInfordevice.getDeviceType().isEmpty()){
//                oldInforDevice.get().setDeviceType(newInfordevice.getDeviceType());
//            }
//
//            if(!newInfordevice.getDeviceURL().isEmpty()){
//                oldInforDevice.get().setDeviceURL(newInfordevice.getDeviceURL());
//            }
//
//            if(!newInfordevice.getDevicePort().isEmpty()){
//                oldInforDevice.get().setDevicePort(newInfordevice.getDevicePort());
//            }
//
//            if(!newInfordevice.getPin().isEmpty()){
//                oldInforDevice.get().setPin(newInfordevice.getPin());
//            }
//
//            deviceInDetailRepository.save(oldInforDevice.get());
//
//            return ResponseEntity.ok("UPDATED");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
//    }
//
//    @GetMapping(path = "/getDeviceById")
//    public ResponseEntity<?> getDeviceById(int id){
//        Optional<DeviceInDetail> device = deviceInDetailRepository.findById(id);
//        if(device.isPresent()){
//            return ResponseEntity.ok(device.get());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
//    }
//
//    @GetMapping(path = "/getType")
//    public ResponseEntity<?> getDeviceType(){
//        return ResponseEntity.ok(deviceTypeRepository.findAll());
//    }
//}
