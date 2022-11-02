package com.example.light_up_travel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String surname;

    @Size(max = 20)
    private String gender;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
    @NotBlank
    @Size(min = 8, max = 120)
    private String password;

    private String country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // rashid's reset password code (int only)
//    @Column(name = "reset_password_code")
//    @JsonIgnore
//    private Integer resetPasswordCode;

    @Column( length = 64)
    private String verificationCode;
    private boolean enabled;

    private Date dob;
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

