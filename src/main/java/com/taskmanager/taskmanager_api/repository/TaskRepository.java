package com.taskmanager.taskmanager_api.repository;

import com.taskmanager.taskmanager_api.model.Task;
import com.taskmanager.taskmanager_api.model.TaskStatus;
import com.taskmanager.taskmanager_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("""
    SELECT t FROM Task t
    WHERE t.user = :user
    AND (:status IS NULL OR t.status = :status)
    AND (:keyword IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
         OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
    AND (:from IS NULL OR t.dueDate >= :from)
    AND (:to IS NULL OR t.dueDate <= :to)
""")
    Page<Task>searchTasks(
            @Param("user") User user,
            @Param("status") TaskStatus status,
            @Param("keyword") String keyword,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable

    );
    Page<Task> findByUser(User user, Pageable pageable);

}

//Applies filters only if values are provided
//Prevents SQL injection (JPQL + parameters)
//Keeps ownership security intact