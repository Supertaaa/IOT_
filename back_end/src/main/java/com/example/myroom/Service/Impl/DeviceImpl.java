package com.example.myroom.Service.Impl;

import com.example.myroom.Entities.DeviceInDetail;
import com.example.myroom.Entities.Juntion;
import com.example.myroom.Repository.DeviceInDetailRepository;
import com.example.myroom.Repository.DeviceTypeRepository;
import com.example.myroom.Repository.JuntionRepository;
import com.example.myroom.Repository.RoomRepository;
import com.example.myroom.Service.DeviceInterface;
import com.example.myroom.Util.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class DeviceImpl implements DeviceInterface {

    @Autowired
    DeviceInDetailRepository deviceInDetailRepository;

    @Autowired
    DeviceTypeRepository deviceTypeRepository;

    @Autowired
    JuntionRepository juntionRepository;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public HashMap<String, Object> getListDetailByRoom(Integer roomId) {

        HashMap<String, Object> result = new HashMap<>();
        result.put("error_code", "0");
        result.put("data", deviceInDetailRepository.findAllByRoomId(roomId));
        return result;

    }

    @Override
    public HashMap<String, Object> getListDetailByUser(Integer userId) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        List<DeviceInDetail> listDevice = new ArrayList<>();
        List<Juntion> listJun = juntionRepository.findAllByUserId(Integer.parseInt(AES.decrypt(String.valueOf(userId), "KEMIATHOIKEMIATH")));

        if(listJun != null){
            for(Juntion juntion:listJun){
                listDevice.addAll(deviceInDetailRepository.findAllByRoomId(juntion.getRoomId()));
            }
            result.put("error_code", "0");
            result.put("data", listDevice);

            return result;
        }
        result.put("error_code", "-1");
        return result;
    }

    @Override
    public HashMap<String, Object> deleteDevice(Integer deviceId) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<DeviceInDetail> device = deviceInDetailRepository.findById(deviceId);
        if(device.isPresent()){
            deviceInDetailRepository.delete(device.get());
            result.put("error_code", "0");
            return result;
        }
        result.put("error_code", "-1");
        result.put("error_message", "NOT FOUND DEVICE");
        return result;
    }

    @Override
    public HashMap<String, Object> createDevice(DeviceInDetail inputDevice) {

        HashMap<String, Object> result = new HashMap<>();
        if(inputDevice != null){
            DeviceInDetail device = new DeviceInDetail();
            device.setName(inputDevice.getName());
            device.setType(inputDevice.getType());
            device.setCreatedDate(LocalDateTime.now());
            device.setCloudId(inputDevice.getCloudId());
            device.setRoomId(inputDevice.getRoomId());
            device.setMoveRange(inputDevice.getMoveRange());
            device.setTypeId(deviceTypeRepository.findByTypeName(inputDevice.getType()).getId());

            device.setStatus(1);
            deviceInDetailRepository.save(device);
            result.put("error_code", "0");

            return result;

        }
        result.put("error_code", "-1");
        result.put("error_message", "NULL INPUT");

        return result;
    }

    @Override
    public HashMap<String, Object> updateDevice(DeviceInDetail newInfordevice) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<DeviceInDetail> oldInforDevice = deviceInDetailRepository.findById(newInfordevice.getId());

        if(oldInforDevice.isPresent()){


            if(!newInfordevice.getName().isEmpty()){
                oldInforDevice.get().setName(newInfordevice.getName());
            }

            if(!newInfordevice.getType().isEmpty()){
                oldInforDevice.get().setType(newInfordevice.getType());
            }

            if(!newInfordevice.getCloudId().isEmpty()){
                oldInforDevice.get().setCloudId(newInfordevice.getCloudId());
            }

            oldInforDevice.get().setUpdatedDate(LocalDateTime.now());

            deviceInDetailRepository.save(oldInforDevice.get());


            result.put("error_code", "0");
            return result;
        }

        result.put("error_code", "-1");
        result.put("error_message", "NOT FOUND DEVICE");


        return result;
    }

    @Override
    public HashMap<String, Object> getDeviceById(Integer deviceId) {

        HashMap<String, Object> result = new HashMap<>();


        Optional<DeviceInDetail> device = deviceInDetailRepository.findById(deviceId);
        if(device.isPresent()){

            result.put("error_code", "0");
            result.put("data", device.get());

            return result;
        }
        result.put("error_code", "-1");
        result.put("error_message", "NOT FOUND DEVICE");

        return result;
    }

    @Override
    public HashMap<String, Object> getAllDeviceType() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("error_code", "0");
        result.put("data", deviceTypeRepository.findAll());
        return result;
    }
}
