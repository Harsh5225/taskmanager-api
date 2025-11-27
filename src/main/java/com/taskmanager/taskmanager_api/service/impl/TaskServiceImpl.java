package com.taskmanager.taskmanager_api.service.impl;


import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.model.User;
import com.taskmanager.taskmanager_api.repository.TaskRepository;
import com.taskmanager.taskmanager_api.repository.UserRepository;
import com.taskmanager.taskmanager_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Task createTask(TaskRequest request){

        // 1. Get logged-in user's email from SecurityContext
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        String email=authentication.getName();

        // 2. Load user from db

        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));


        // 3. Create Task
        Task task=Task.builder()
                .title(request.getTitle())
                .description((request.getDescription()))
                .status(request.getStatus())
                .dueDate(request.getDueDate())
                .user(user)
                .build();

        // 4. save Task
        return taskRepository.save(task);

    }

    @Override
    public List<Task>getMyTasks(){
        // 1. Get logged-in user's email from SecurityContext
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();

        // 2. Load user
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found."));

        // 3. fetch only this user's tasks

        return taskRepository.findByUser(user);


    }
}



//link JWT → User → Tasks
//  Authentication authentication =
//        SecurityContextHolder.getContext().getAuthentication();
//String email = authentication.getName();
//This is the direct connection between JWT