package com.example.light_up_travel.entity;

import com.example.light_up_travel.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 50)
    private String name;

    @Column(nullable = false)
    @Size(max = 100)
    private String surname;

    @Size(max = 20)
    private String gender;
    @Column(nullable = false)
    @Size(max = 50)
    @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false)
    @Size(min = 8, max = 120)
    private String password;

    private String country;

    private String profileUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @Column( length = 64)
    private String verificationCode;
    private boolean enabled;

    @Enumerated
    private Status status;

    private LocalDate dob;
    @Column(name = "date_created" /*, nullable = false **/)
    private Date dateCreated;
    @Column(name = "date_updated")
    private Date dateUpdated;
    @Column(name = "date_deleted")
    private Date dateDeleted;

    public String getUsername(){
        return email;
    }

    public User(String name, String surname,String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.enabled =false;
        this.dateCreated = new Date();
    }
}

