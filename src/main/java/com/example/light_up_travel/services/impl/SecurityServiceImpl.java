package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.model.PasswordResetToken;
import com.example.light_up_travel.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class SecurityServiceImpl {

    private Logger logger;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (!isTokenFound(passToken)){
                throw new RuntimeException("Token not found");
            }
        else if (isTokenExpired(passToken)) {
                throw new RuntimeException("Token expired");
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
