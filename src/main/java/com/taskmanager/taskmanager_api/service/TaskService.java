package com.taskmanager.taskmanager_api.service;


import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.dto.TaskResponse;
import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.model.TaskStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);

    Page<TaskResponse> getMyTasks(int page, int size, String sortBy, String direction);

    TaskResponse updateTask(Long taskId, TaskRequest request);

    void deleteTask(Long taskId);

    Page<TaskResponse>searchTasks(
            TaskStatus status,
            String keyword,
            LocalDate from,
            LocalDate to,
            int page,
            int size,
            String sortBy,
            String direction

    );
}
