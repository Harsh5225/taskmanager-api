package com.taskmanager.taskmanager_api.repository;

import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task>findByUser(User user);

}
