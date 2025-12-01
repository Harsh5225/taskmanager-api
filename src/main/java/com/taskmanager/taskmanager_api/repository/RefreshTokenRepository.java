package com.taskmanager.taskmanager_api.repository;

import com.taskmanager.taskmanager_api.model.RefreshToken;
import com.taskmanager.taskmanager_api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken>findByToken(String token);

    @Transactional
    @Modifying
    void deleteByUser(User user);
}


//@Modifying → to allow delete/update
//@Transactional → to safely execute DB mutation