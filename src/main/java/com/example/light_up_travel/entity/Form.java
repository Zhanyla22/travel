package com.example.light_up_travel.entity;

import com.example.light_up_travel.enums.Stat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

//    @Column(name = "way_of_contacting")
//    private String wayOfContacting;
    private Stat status;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "date_created" /*, nullable = false **/)
    private Date dateCreated;
    @Column(name = "date_updated")
    private Date dateUpdated;
    @Column(name = "date_deleted")
    private Date dateDeleted;

//    public Form(String description, Stat status) {
//        this.description = description;
//        this.status = status;
//        this.dateCreated = new Date();
//    }
}