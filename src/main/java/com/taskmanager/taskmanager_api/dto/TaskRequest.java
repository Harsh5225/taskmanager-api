package com.taskmanager.taskmanager_api.dto;

import com.taskmanager.taskmanager_api.model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
}
//Keeps API clean
//Avoids exposing DB structure
//Safer for future changes
