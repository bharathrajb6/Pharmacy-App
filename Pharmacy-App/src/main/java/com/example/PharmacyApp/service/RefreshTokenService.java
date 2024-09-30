package com.example.PharmacyApp.service;

import com.example.PharmacyApp.model.Persistance.RefreshToken;
import com.example.PharmacyApp.model.Persistance.User;
import com.example.PharmacyApp.repository.RefreshTokenRepository;
import com.example.PharmacyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            RefreshToken refreshToken = RefreshToken.builder().user(user).token(UUID.randomUUID().toString()).expiryDate(Instant.now().plusMillis(600000)).build();
            return refreshTokenRepository.save(refreshToken);
        }
        return null;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh token is expired");
        }
        return refreshToken;
    }

}
