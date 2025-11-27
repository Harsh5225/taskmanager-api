package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // create task
    @PostMapping
    public Task createTask(@RequestBody TaskRequest request){
        return taskService.createTask(request);
    }

    @GetMapping
    public List<Task> getMyTasks(){
        return taskService.getMyTasks();
    }
}
