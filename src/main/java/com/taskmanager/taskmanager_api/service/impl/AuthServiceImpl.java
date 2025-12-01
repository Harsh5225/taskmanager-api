package com.taskmanager.taskmanager_api.service.impl;

import com.taskmanager.taskmanager_api.dto.AuthResponse;
import com.taskmanager.taskmanager_api.dto.LoginRequest;
import com.taskmanager.taskmanager_api.dto.RegisterRequest;
import com.taskmanager.taskmanager_api.model.RefreshToken;
import com.taskmanager.taskmanager_api.model.Role;
import com.taskmanager.taskmanager_api.model.User;
import com.taskmanager.taskmanager_api.repository.RefreshTokenRepository;
import com.taskmanager.taskmanager_api.repository.UserRepository;
import com.taskmanager.taskmanager_api.security.JwtUtil;
import com.taskmanager.taskmanager_api.service.AuthService;
import com.taskmanager.taskmanager_api.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;


    @Override
    public String register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already exists");
        }

        User user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
    @Override
    public AuthResponse login(LoginRequest request){

        // authenticate email + password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        String accessToken=jwtUtil.generateToken(user.getEmail());
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user);
        return new AuthResponse(accessToken,refreshToken.getToken());

    }
}
