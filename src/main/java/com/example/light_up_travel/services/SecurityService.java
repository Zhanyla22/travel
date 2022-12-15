package com.example.light_up_travel.services;

import com.example.light_up_travel.entity.PasswordResetToken;
import com.example.light_up_travel.exceptions.ResetPasswordCodeExpirationException;
import com.example.light_up_travel.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private Logger logger;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (!isTokenFound(passToken)) {
            throw new RuntimeException("Token not found");
        } else if (isTokenExpired(passToken)) {
            throw new ResetPasswordCodeExpirationException("Token expired");
        }
        return passToken;

    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

}
