package com.example.myroom.Service;

import com.example.myroom.Entities.User;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface UserInterface {
    HashMap<String, Object> login(User user) throws Exception;
    HashMap<String, Object> createUser(User user, String token) throws Exception;
    HashMap<String, Object> resetPassword(String username);
    HashMap<String, Object> verifyResetPassword(String username, String token);
    HashMap<String, Object> changePass(String token, String username, String password);
    HashMap<String, Object> verifyEmail(String email);
}
