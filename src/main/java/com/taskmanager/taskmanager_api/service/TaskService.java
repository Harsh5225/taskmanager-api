package com.taskmanager.taskmanager_api.service;


import com.taskmanager.taskmanager_api.dto.TaskRequest;
import com.taskmanager.taskmanager_api.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(TaskRequest request);
    List<Task>getMyTasks();
}
