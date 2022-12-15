package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > {

    Optional <User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

    @Query("SELECT u FROM User u join u.roles as r WHERE 'ROLE_USER' = r.name and u.dateDeleted is null")
    List<User> findAllUserRoles();

    @Query("SELECT u FROM User u join u.roles as r WHERE 'ROLE_MODERATOR' = r.name and u.dateDeleted is null")
    List<User> findAllNotDeletedModerators();

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.dateDeleted is not null")
    List<User> findAllDeletedUsers();
    @Query("SELECT u FROM User u WHERE u.dateDeleted is null and u.id = ?1")
    User findNotDeletedUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.status=0")
    List<User> findAllNotDeletedUsers();
}
