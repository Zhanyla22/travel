package com.example.light_up_travel.entity;


import com.example.light_up_travel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "dateCreated")
    private LocalDate dateCreated;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "dateUpdated")
    private LocalDateTime dateUpdated;

    @Column(name = "dateDeleted")
    private LocalDateTime dateDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
