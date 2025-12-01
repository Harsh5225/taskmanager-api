package com.taskmanager.taskmanager_api.service;

import com.taskmanager.taskmanager_api.dto.AuthResponse;
import com.taskmanager.taskmanager_api.dto.LoginRequest;
import com.taskmanager.taskmanager_api.dto.RegisterRequest;
import com.taskmanager.taskmanager_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface AuthService {
   String register(RegisterRequest request);
   AuthResponse login(LoginRequest request);
}
