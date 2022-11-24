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
@Table(name = "forum")
public class Forum {

    @Id
    private Long id;

    @Column(nullable = false)
    private String description;

//    @Column(name = "way_of_contacting")
//    private String wayOfContacting;

    @Enumerated(EnumType.STRING)
    private Stat status;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "date_created" /*, nullable = false **/)
    private Date dateCreated;
    @Column(name = "date_updated")
    private Date dateUpdated;
    @Column(name = "date_deleted")
    private Date dateDeleted;

    public Forum(String description, User basicUserDtoToUser) {
        this.description = description;
        this.user = basicUserDtoToUser;
    }
}