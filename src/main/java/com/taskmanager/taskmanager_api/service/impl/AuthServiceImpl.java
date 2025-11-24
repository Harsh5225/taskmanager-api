package com.taskmanager.taskmanager_api.service.impl;

import com.taskmanager.taskmanager_api.dto.LoginRequest;
import com.taskmanager.taskmanager_api.dto.RegisterRequest;
import com.taskmanager.taskmanager_api.service.AuthService;

public class AuthServiceImpl implements AuthService {
    @Override
    public String register(RegisterRequest request){
        return "REGISTER_LOGIC_PENDING";
    }
    @Override
    public String login(LoginRequest request){
        return "LOGIN_LOGIC_PENDING";
    }
}
