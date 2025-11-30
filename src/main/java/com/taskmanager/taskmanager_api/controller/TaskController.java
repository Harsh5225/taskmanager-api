package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.dto.TaskResponse;
import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // create task
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request){
        return taskService.createTask(request);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<TaskResponse> getMyTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return taskService.getMyTasks(page, size, sortBy, direction);
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                           @RequestBody TaskRequest request){
        return taskService.updateTask(id, request);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task deleted successfully";
    }
}
