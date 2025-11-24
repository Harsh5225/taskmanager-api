package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.LoginRequest;
import com.taskmanager.taskmanager_api.dto.RegisterRequest;
import com.taskmanager.taskmanager_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request)
    {
        return authService.register(request);
    }

    @GetMapping("/login")
    public String login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

}
