package com.example.light_up_travel.repository;

import com.example.light_up_travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > {

    Optional <User> findByEmail(String email);

//    Optional <User> findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

    Boolean existsByEmail(String email);

//    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.dateDeleted is null and u.id = ?1")
    User findNotDeletedUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.dateDeleted is null")
    List<User> findAllNotDeletedUsers();
}
