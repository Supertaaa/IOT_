package com.example.myroom.Service;

import com.example.myroom.Entities.DeviceInDetail;
import org.springframework.http.ResponseEntity;

public interface DeviceInterface {

    ResponseEntity<?> getListDetailByRoom(Integer roomId);
    ResponseEntity<?> getListDetailByUser(Integer userId);
    ResponseEntity<?> deleteDevice(Integer deviceId);
    ResponseEntity<?> createDevice(DeviceInDetail device);
    ResponseEntity<?> updateDevice(DeviceInDetail device);
    ResponseEntity<?> getDeviceById(Integer deviceId);
    ResponseEntity<?> getAllDeviceType();

    //Và cac chức năng dieu khien thiet bi.
}
