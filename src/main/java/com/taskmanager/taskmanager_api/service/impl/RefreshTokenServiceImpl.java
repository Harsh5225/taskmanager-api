package com.taskmanager.taskmanager_api.service.impl;

import com.taskmanager.taskmanager_api.model.RefreshToken;
import com.taskmanager.taskmanager_api.model.User;
import com.taskmanager.taskmanager_api.repository.RefreshTokenRepository;
import com.taskmanager.taskmanager_api.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {


    // 7 days expiry
    private static final long REFRESH_TOKEN_DURATION_MS = 7 * 24 * 60 * 60 * 1000;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(User user) {
       // remove old refresh token if exists (1 token per user)

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken=RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_DURATION_MS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token has expired. Plase login again.");
        }
        return token;
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
// Features:
//Secure one refresh token per user
//Automatic cleanup of expired tokens
//Token rotation foundation