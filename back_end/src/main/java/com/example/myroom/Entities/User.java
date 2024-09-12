package com.example.myroom.Entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private Integer status; //1:Active, 0:Suspend

    private LocalDateTime createdDate;

    private LocalDateTime updateDate;

    private Integer role; //0:User, 1:Admin

}
