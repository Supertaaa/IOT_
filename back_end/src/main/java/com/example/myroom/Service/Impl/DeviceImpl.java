package com.example.myroom.Service.Impl;

import com.example.myroom.Entities.DeviceInDetail;
import com.example.myroom.Service.DeviceInterface;
import org.springframework.http.ResponseEntity;

public class DeviceImpl implements DeviceInterface {
    @Override
    public ResponseEntity<?> getListDetailByRoom(Integer roomId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getListDetailByUser(Integer userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteDevice(Integer deviceId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createDevice(DeviceInDetail device) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateDevice(DeviceInDetail device) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDeviceById(Integer deviceId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllDeviceType() {
        return null;
    }
}
