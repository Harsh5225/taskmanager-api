package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.AuthResponse;
import com.taskmanager.taskmanager_api.dto.LoginRequest;
import com.taskmanager.taskmanager_api.dto.RegisterRequest;
import com.taskmanager.taskmanager_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    // authservice
    private final AuthService authService;


    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request)
    {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }


}
