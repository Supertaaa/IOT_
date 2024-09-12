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

    public HashMap<String, Object> login(User user) throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        if (!userRepository.existsUserByUserName(user.getUserName())){
            result.put("error_code", "-1");
            result.put("error_message", "NOT FOUND");
            return result;
        }
        User u = userRepository.findByUserName(user.getUserName()); //gets user object
        String password = u.getPassWord();
        if (!BCrypt.checkpw(user.getPassWord(), password)){
            result.put("error_code", "-1");
            result.put("error_message", "WRONG PASSWORD");
            return result;
        }
        result.put("error_code", "0");
        result.put("emanu", AES.encrypt(u.getUserName(),secretKey));
        //result.put("resudi", AES.encrypt(Integer.toString(u.getId()),"KEMIATHOIKEMIATH"));
        result.put("firstName", u.getFirstName());
        result.put("lastName", u.getLastName());
        result.put("token", TokenAuthenticationService.getToken(user.getUserName()));

        return result;
    }

    public HashMap<String, Object> createUser(User user, String token) throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        if(tokenRepository.findByUserIdAndValueAndType(user.getId(),token, 0) == null){
            result.put("error_code", "-1");
            result.put("error_message", "CODE DOES NOT MATCH");
            return result;
        }

        //if (userRepository.existsUserByUserName(user.getUserName())){return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("USER EXISTED");}
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassWord(encoder.encode(user.getPassWord()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setCreatedDate(LocalDateTime.now());
        newUser.setStatus(1);
        newUser.setRole(0);
        userRepository.save(newUser);


        result.put("error_code", "0");
        result.put("emanu", AES.encrypt(newUser.getUserName(),secretKey));
        //result.put("resudi", AES.encrypt(Integer.toString(newUser.getId()),"KEMIATHOIKEMIATH"));
        result.put("firstName", newUser.getFirstName());
        result.put("lastName", newUser.getLastName());
        result.put("token", TokenAuthenticationService.getToken(user.getUserName()));

        return result;
    }

    public HashMap<String, Object> resetPassword(String username) {


        HashMap<String, Object> result = new HashMap<>();


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
                result.put("error_code", "0");
                return result;
            }
        }
        result.put("error_code", "-1");
        result.put("error_message", "CANNOT RESOLVE");

        return result;
    }

    public HashMap<String, Object> verifyResetPassword(String username, String token) {

        User user = userRepository.findByUserName(username);

        HashMap<String, Object> result = new HashMap<>();

        if(user != null){

            if(tokenRepository.findByUserIdAndType(user.getId(), 1).getValue().equals(token)){
//                Random random = new Random();
//                int randomPass = random.nextInt(900000) + 100000;
//                user.setPassWord(Integer.toString(randomPass));
//                userRepository.save(user);
                result.put("error_code", "0");
                return result;
            }
        }

        result.put("error_code", "-1");
        result.put("error_message", "CODE DOES NOT MATCH OR EMAIL DOES NOT EXIST");
        return result;
    }

    public HashMap<String, Object> changePass(String token, String username, String password) {

        HashMap<String, Object> result = new HashMap<>();
        User user = userRepository.findByUserName(username);
        if (user != null){

            System.out.println("NGU");
            if (tokenRepository.findByUserIdAndType(user.getId(), 1).getValue().equals(token)){
                result.put("error_code", "0");
                user.setPassWord(encoder.encode(password));
                user.setUpdateDate(LocalDateTime.now());
                userRepository.save(user);


                return result;
            }
        }
        result.put("error_code", "-1");
        result.put("error_message", "CODE DOES NOT MATCH OR EMAIL DOES NOT EXIST");

        return result;
    }

    public HashMap<String, Object> verifyEmail(String username) {

        HashMap<String, Object> result = new HashMap<>();

        if(userRepository.existsUserByUserName(username)){

            result.put("error_code", "-1");
            result.put("error_message", "USER ALREADY EXISTS");

            return result;
        }

        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;

        String content = "<h1>Verify Code</h1><p>Your code: <strong>" + randomNumber + "</strong></p>";
        String response = SendEmail.send(username,"Mar Heaven sends your verify code", content);
        if(response.equals("OK")){

            Token verifyToken = new Token();
            verifyToken.setUserId(userRepository.findByUserName(username).getId());
            verifyToken.setValue(String.valueOf(randomNumber));
            verifyToken.setType(0);
            verifyToken.setExpiredTime(LocalDateTime.now().plusMinutes(minute));
            verifyToken.setCreateDate(LocalDateTime.now());

            tokenRepository.save(verifyToken);
            result.put("error_code", "0");
            return result;
        }
        result.put("error_code", "-1");
        result.put("error_message", "EMAIL DOES NOT EXIST!");

        return result;
    }
}
