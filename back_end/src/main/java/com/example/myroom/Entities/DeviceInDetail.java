package com.example.myroom.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class DeviceInDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String type;

    private Integer typeId;

    private LocalDateTime createdDate;

    private String cloudId;

    private String moveRange;

    private Integer roomId;

    private Integer status = 1;
}
