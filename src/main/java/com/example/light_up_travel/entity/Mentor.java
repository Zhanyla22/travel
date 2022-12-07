package com.example.light_up_travel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "mentee_id")
    private Long menteeId;

    @Column(name = "mentor_id")
    private Long mentorId;

    public Mentor(Long menteeId, Long mentorId) {
        this.menteeId = menteeId;
        this.mentorId = mentorId;
    }
}
