package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.model.User;
import com.taskmanager.taskmanager_api.repository.TaskRepository;
import com.taskmanager.taskmanager_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/tasks/{id}")
    public void deleteAnyTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
