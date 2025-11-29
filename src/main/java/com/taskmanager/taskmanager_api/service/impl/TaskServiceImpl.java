package com.taskmanager.taskmanager_api.service.impl;

import com.taskmanager.taskmanager_api.dto.TaskResponse;
import com.taskmanager.taskmanager_api.exception.ResourceNotFoundException;
import com.taskmanager.taskmanager_api.exception.AccessDeniedException;
import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.model.User;
import com.taskmanager.taskmanager_api.repository.TaskRepository;
import com.taskmanager.taskmanager_api.repository.UserRepository;
import com.taskmanager.taskmanager_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public TaskResponse createTask(TaskRequest request){

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
        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);


    }

    @Override
    public Page<TaskResponse> getMyTasks(int page, int size, String sortBy, String direction) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return taskRepository.findByUser(user, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public TaskResponse updateTask(Long taskId, TaskRequest request){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));

        // get task
        Task task=taskRepository.findById(taskId)
                .orElseThrow(()->new ResourceNotFoundException("Task not found."));

        //update task
        if(!task.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);

    }

    @Override
    public void deleteTask(Long taskId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        String email=authentication.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("user not found"));

        Task task=taskRepository.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));

        // ownership check
        if(!task.getUser().getId().equals(user.getId()))
        {
            throw new AccessDeniedException("You are not allowed to delete this task");

        }

        taskRepository.delete(task);
    }


    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .userId(task.getUser().getId())
                .userEmail(task.getUser().getEmail())
                .build();
    }


}



//link JWT → User → Tasks
//  Authentication authentication =
//        SecurityContextHolder.getContext().getAuthentication();
//String email = authentication.getName();
//This is the direct connection between JWT