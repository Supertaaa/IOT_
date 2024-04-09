package com.example.myroom.Entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    private String lastName;

    private String firstName;

    private String passWord;

    private String status;

    private LocalDate createdDate;

    private String recoverPassCode;

    private String vertifyCode;

    private String role;



}
