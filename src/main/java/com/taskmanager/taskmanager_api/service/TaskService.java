package com.taskmanager.taskmanager_api.service;


import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.dto.TaskResponse;
import com.taskmanager.taskmanager_api.model.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);

    Page<TaskResponse> getMyTasks(int page, int size, String sortBy, String direction);

    TaskResponse updateTask(Long taskId, TaskRequest request);

    void deleteTask(Long taskId);
}
