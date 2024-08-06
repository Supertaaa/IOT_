//package com.example.myroom.Controller;
//
//
//import com.example.myroom.Entities.User;
//import com.example.myroom.Repository.UserRepository;
//import com.example.myroom.Util.AES;
//import com.example.myroom.Util.SendEmail;
//import com.example.myroom.Util.TokenAuthenticationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Random;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//
//@RestController
//@RequestMapping(path = "/user")
//public class UserController {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    private String code;
//
//    @PostMapping(path = "/login")
//    public ResponseEntity<?> login(User user) throws Exception {
//
//        HashMap<String, Object> result = new HashMap<>();
//        if (!userRepository.existsUserByUserName(user.getUserName())){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");}
//        User u = userRepository.findByUserName(user.getUserName()); //gets user object
//        String password = u.getPassWord();
//        if (!BCrypt.checkpw(user.getPassWord(), password)){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND USER");
//        }
//        result.put("emanu", AES.encrypt(u.getUserName(),"KEMIATHOIKEMIATH"));
//        result.put("resudi", AES.encrypt(Integer.toString(u.getId()),"KEMIATHOIKEMIATH"));
//        result.put("firstName", u.getFirstName());
//        result.put("lastName", u.getLastName());
//        result.put("token", TokenAuthenticationService.getToken(user.getUserName()));
//
//        return ResponseEntity.ok(result);
//    }
//
//
//    @PostMapping(path = "/createUser")
//    public ResponseEntity<?> createUser(User user) throws Exception {
//        if(!user.getVertifyCode().equals(code)){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CODE DOES NOT MATCH");
//        }
//        HashMap<String, Object> result = new HashMap<>();
//        if (userRepository.existsUserByUserName(user.getUserName())){return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("NOT FOUND USER");}
//        User newUser = new User();
//        newUser.setUserName(user.getUserName());
//        newUser.setPassWord(encoder.encode(user.getPassWord()));
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setCreatedDate(LocalDate.now());
//        newUser.setStatus("Active");
//        userRepository.save(newUser);
//
//        result.put("emanu", AES.encrypt(newUser.getUserName(),"KEMIATHOIKEMIATH"));
//        result.put("resudi", AES.encrypt(Integer.toString(newUser.getId()),"KEMIATHOIKEMIATH"));
//        result.put("firstName", newUser.getFirstName());
//        result.put("lastName", newUser.getLastName());
//        result.put("token", TokenAuthenticationService.getToken(user.getUserName()));
//
//        return ResponseEntity.ok(result);
//    }
//
//    @GetMapping(path = "/reset_password")
//    public ResponseEntity<?> resetPassword(String email){
//
//        Random random = new Random();
//        int randomNumber = random.nextInt(900000) + 100000;
//        String content = "<h1>Password Reset</h1><p>Your reset code: <strong>" + randomNumber + "</strong></p>";
//        String response = SendEmail.send(email,"Mar Heaven sends your reset password", content);
//
//        User user = userRepository.findByUserName(email);
//        if (user != null){
//            user.setRecoverPassCode(Integer.toString(randomNumber));
//            userRepository.save(user);
//            if(response.equals("OK")){
//                return ResponseEntity.ok("OK");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CANNOT RESOLVE");
//    }
//
//    @GetMapping(path = "/vertify_reset_password")
//    public ResponseEntity<?> vertifyResetPassword(String email, String code){
//
//        User user = userRepository.findByUserName(email);
//
//        if(user != null){
//            if(user.getRecoverPassCode().equals(code)){
//                Random random = new Random();
//                int randomPass = random.nextInt(900000) + 100000;
//                user.setPassWord(Integer.toString(randomPass));
//                userRepository.save(user);
//                return ResponseEntity.ok("OK");
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CODE DOES NOT MATCH OR EMAIL DOES NOT EXIST");
//    }
//
//    @PostMapping(path = "/changePassword")
//    public ResponseEntity<?> changePass(String code, String userName, String pass){
//        User user = userRepository.findByUserName(userName);
//        if (user != null){
//
//            System.out.println("NGU");
//            if (user.getRecoverPassCode().equals(code)){
//                user.setPassWord(encoder.encode(pass));
//                userRepository.save(user);
//                return ResponseEntity.ok("OK");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CODE DOES NOT MATCH OR EMAIL DOES NOT EXIST");
//    }
//
//
//    @GetMapping(path = "/vertify_email")
//    public ResponseEntity<?> vertifyEmail(String email){
//
//        Random random = new Random();
//        int randomNumber = random.nextInt(900000) + 100000;
//
//        String content = "<h1>Verify Code</h1><p>Your code: <strong>" + randomNumber + "</strong></p>";
//        String response = SendEmail.send(email,"Mar Heaven sends your vertify code", content);
//        if(response.equals("OK")){
//            this.code = Integer.toString(randomNumber);
//            System.out.println(this.code);
//            return ResponseEntity.ok("SEND");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMAIL DOES NOT EXIST!");
//    }
//}
