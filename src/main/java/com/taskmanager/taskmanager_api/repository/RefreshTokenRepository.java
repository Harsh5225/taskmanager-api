package com.taskmanager.taskmanager_api.repository;

import com.taskmanager.taskmanager_api.model.RefreshToken;
import com.taskmanager.taskmanager_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken>findByToken(String token);
    void deleteByUser(User user);
}
