package com.example.light_up_travel.entity;

import com.example.light_up_travel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name ="city")
    private String city;

    @Column(name ="address")
    private String address;

    @Column(name ="description")
    private String description;

    @Column(name ="mainFilePath")
    private String mainFilePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name ="dateCreated")
    private LocalDate dateCreated;

    @Column(name ="dateUpdated")
    private LocalDate dateUpdated;

    @Column(name ="dateDeleted")
    private LocalDateTime dateDeleted;
}
