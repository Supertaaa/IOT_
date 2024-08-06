package com.example.myroom.Service;

import com.example.myroom.Entities.User;
import org.springframework.http.ResponseEntity;

public interface UserInterface {
    ResponseEntity<?> login(User user) throws Exception;
    ResponseEntity<?> createUser(User user, String token) throws Exception;
    ResponseEntity<?> resetPassword(String username);
    ResponseEntity<?> verifyResetPassword(String username, String token);
    ResponseEntity<?> changePass(String token, String username, String password);
    ResponseEntity<?> verifyEmail(String email);
}
