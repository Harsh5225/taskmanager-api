package com.taskmanager.taskmanager_api.service;

import com.taskmanager.taskmanager_api.model.RefreshToken;
import com.taskmanager.taskmanager_api.model.User;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken>findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    void deleteByUser(User user);
}
