package com.example.myroom.Service;

import com.example.myroom.Entities.DeviceInDetail;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface DeviceInterface {

    HashMap<String, Object> getListDetailByRoom(Integer roomId);
    HashMap<String, Object> getListDetailByUser(Integer userId) throws Exception;
    HashMap<String, Object> deleteDevice(Integer deviceId);
    HashMap<String, Object> createDevice(DeviceInDetail device);
    HashMap<String, Object> updateDevice(DeviceInDetail device);
    HashMap<String, Object> getDeviceById(Integer deviceId);
    HashMap<String, Object> getAllDeviceType();

    //Và cac chức năng dieu khien thiet bi.
}
