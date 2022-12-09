package com.example.light_up_travel.entity;

import com.example.light_up_travel.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Place {

    private Long id;

    private String name;

    private String description;

    private String mainFilePath;

    private Status status;

    private LocalDate dateCreated;

    private LocalDate dateUpdated;

    private LocalDateTime dateDeleted;
}
