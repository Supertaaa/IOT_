package com.example.myroom.Controller;


import com.example.myroom.Entities.DeviceInDetail;
import com.example.myroom.Entities.User;
import com.example.myroom.Repository.DeviceInDetailRepository;
import com.example.myroom.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/device")
public class DeviceController {

    @Autowired
    DeviceInDetailRepository deviceInDetailRepository;
    
    @GetMapping(path = "/test")
    public ResponseEntity<?> testConnection(String url, String port){
        ResponseEntity<String> response = null;
        String apiUrl = "http://"+ url + ":" + port +"/test";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        MultiValueMap<String, String> inputMap = new LinkedMultiValueMap<>();
        HttpEntity request = new HttpEntity(inputMap, headers);
        try{
            response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, String.class);

            if(response.getStatusCode().value() == 200){
                return ResponseEntity.ok(response.getBody());
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");

    }

    @GetMapping(path = "/switch-controller")
    public ResponseEntity<?> switchController(String id){
        Optional<DeviceInDetail> device = deviceInDetailRepository.findById(Integer.parseInt(id));
        if(device.isPresent()){
            String url = device.get().getDeviceURL();
            String port = device.get().getDevicePort();
            String status = device.get().getStatus();
            String pin = device.get().getPin();
            String servoRange = device.get().getServoRange();
            String type = device.get().getDeviceType();

            ResponseEntity<String> response = null;
            String apiUrl = "http://"+ url + ":" + port +"/switch_controller?";
            System.out.println(apiUrl);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            //Map<String, String> map = new HashMap<String, String>();
            if(type.equals("SWITCH SERVO")){
                String[] range = servoRange.split(",");
                apiUrl = apiUrl + "range1" + "=" + range[0] + "&";
                apiUrl = apiUrl + "range2" + "=" + range[1] + "&";
            }
            if(status.equals("OFF")){
                //map.put("status", "OFF");
                apiUrl = apiUrl + "status" + "=" + "OFF&";

            }
            else if (status.equals("ON")){
                //map.put("status", "ON");
                apiUrl = apiUrl + "status" + "=" + "ON&";
            }
            apiUrl = apiUrl + "pin" + "=" + pin + "&";
            apiUrl = apiUrl + "type" + "=" + type;
            System.out.println(apiUrl);
            //map.put("pin", pin);


            MultiValueMap<String, String> inputMap = new LinkedMultiValueMap<>();

            HttpEntity request = new HttpEntity(inputMap, headers);
            System.out.println(request.toString());
            try{
                response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, String.class);

                if(response.getStatusCode().value() == 200){
                    System.out.println(response.getBody());
                    device.get().setStatus(response.getBody());
                    deviceInDetailRepository.save(device.get());
                    return ResponseEntity.ok(response.getBody());
                }
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
            }


        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND DEVICE");
    }
}
