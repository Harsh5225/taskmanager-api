package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.AuthResponse;
import com.taskmanager.taskmanager_api.dto.LoginRequest;
import com.taskmanager.taskmanager_api.dto.RegisterRequest;
import com.taskmanager.taskmanager_api.exception.TooManyRequestsException;
import com.taskmanager.taskmanager_api.service.AuthService;
import com.taskmanager.taskmanager_api.service.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    // authservice
    private final AuthService authService;

    @Autowired
    private RateLimitService rateLimitService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request)
    {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest){
        String ip=httpRequest.getRemoteAddr();

        if(!rateLimitService.allowLoginRequests(ip)){
            throw new TooManyRequestsException(
                    "Too many login attempts. Try again after 1 minute."
            );
        }
        return ResponseEntity.ok(authService.login(request));
    }
}


//Client hits /auth/login
//Server extracts IP address
//IP goes into RateLimitService
//Bucket checks token:
//✅ token available → login allowed
//❌ no token → exception thrown
//Exception caught by GlobalExceptionHandler
//Client receives 429