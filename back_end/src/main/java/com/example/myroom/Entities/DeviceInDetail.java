package com.example.myroom.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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

    private String deviceType;

    private String deviceURL;

    private String devicePort;

    private LocalDate createdDate;

    private String pin;

    private String servoRange;

    private String roomId;

    @Column(columnDefinition = "DEFAULT 'OFF'")
    private String status;

}
