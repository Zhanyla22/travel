package com.example.light_up_travel.entity;



import com.example.light_up_travel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;


    @Column(name="file_path")
    private String filePath;

    @Column(name = "description")
    private String description;


    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "text")
    private String text;


    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Column(name = "date_deleted")
    private LocalDateTime dateDeleted;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
