package com.example.myroom.Service.Impl;

import com.example.myroom.Entities.Token;
import com.example.myroom.Entities.User;
import com.example.myroom.Repository.TokenRepository;
import com.example.myroom.Repository.UserRepository;
import com.example.myroom.Service.UserInterface;
import com.example.myroom.Util.AES;
import com.example.myroom.Util.SendEmail;
import com.example.myroom.Util.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Random;

import java.time.LocalDateTime;
import java.util.HashMap;


@Service
public class UserImpl implements UserInterface {

    @Value("${aes.secret.key}")
    private String secretKey;

    @Value("${password.token.available.in.minute}")
    private Long minute;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<?> login(User user) throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        if (!userRepository.existsUserByUserName(user.getUserName())){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");}
        User u = userRepository.findByUserName(user.getUserName()); //gets user object
        String password = u.getPassWord();
        if (!BCrypt.checkpw(user.getPassWord(), password)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND USER");
        }
        result.put("emanu", AES.encrypt(u.getUserName(),secretKey));
        //result.put("resudi", AES.encrypt(Integer.toString(u.getId()),"KEMIATHOIKEMIATH"));
        result.put("firstName", u.getFirstName());
        result.put("lastName", u.getLastName());
        result.put("token", TokenAuthenticationService.getToken(user.getUserName()));

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> createUser(User user, String token) throws Exception {
        if(tokenRepository.findByUserIdAndValueAndType(user.getId(),token, 0) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CODE DOES NOT MATCH");
        }
        HashMap<String, Object> result = new HashMap<>();
        if (userRepository.existsUserByUserName(user.getUserName())){return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("USER EXISTED");}
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassWord(encoder.encode(user.getPassWord()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setCreatedDate(LocalDateTime.now());
        newUser.setStatus(1);
        newUser.setRole(0);
        userRepository.save(newUser);
        result.put("emanu", AES.encrypt(newUser.getUserName(),secretKey));
        //result.put("resudi", AES.encrypt(Integer.toString(newUser.getId()),"KEMIATHOIKEMIATH"));
        result.put("firstName", newUser.getFirstName());
        result.put("lastName", newUser.getLastName());
        result.put("token", TokenAuthenticationService.getToken(user.getUserName()));

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> resetPassword(String username) {

        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        String content = "<h1>Password Reset</h1><p>Your reset code: <strong>" + randomNumber + "</strong></p>";
        String response = SendEmail.send(username,"Mar Heaven sends your reset password", content);

        User user = userRepository.findByUserName(username);
        if (user != null){

            Token token = tokenRepository.findByUserIdAndType(user.getId(), 1);

            if(token != null){
                token.setValue(String.valueOf(randomNumber));
                token.setCreateDate(LocalDateTime.now());
                token.setExpiredTime(LocalDateTime.now().plusMinutes(minute));
                tokenRepository.save(token);
            }
            else {
                Token resetPassToken = new Token();
                resetPassToken.setUserId(user.getId());
                resetPassToken.setType(1);
                resetPassToken.setValue(String.valueOf(randomNumber));
                resetPassToken.setCreateDate(LocalDateTime.now());
                resetPassToken.setExpiredTime(LocalDateTime.now().plusMinutes(minute));
            }


            if(response.equals("OK")){
                return ResponseEntity.ok("OK");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CANNOT RESOLVE");
    }

    public ResponseEntity<?> verifyResetPassword(String username, String token) {
        return null;
    }

    public ResponseEntity<?> changePass(String token, String username, String password) {
        return null;
    }

    public ResponseEntity<?> verifyEmail(String email) {
        return null;
    }
}
