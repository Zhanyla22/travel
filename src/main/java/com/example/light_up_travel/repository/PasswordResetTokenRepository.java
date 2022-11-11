package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query("SELECT t FROM PasswordResetToken t where t.token = ?1")
    PasswordResetToken findByToken(String token);
    @Query("SELECT t FROM PasswordResetToken t where t.user = ?1")
    PasswordResetToken findByUserId(Long userId);
}