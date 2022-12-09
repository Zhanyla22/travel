package com.example.light_up_travel.entity;

import com.example.light_up_travel.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String mainFilePath;

    private Status status;

    private LocalDate dateCreated;

    private LocalDate dateUpdated;

    private LocalDateTime dateDeleted;
}
