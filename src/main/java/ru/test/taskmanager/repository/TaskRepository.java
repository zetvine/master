package ru.test.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.taskmanager.model.Task;
import ru.test.taskmanager.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByExecutionTimeBetween(LocalDateTime moment1, LocalDateTime moment2);

    List<Task> findByExecutionTimeBetweenAndTaskStatus(LocalDateTime moment1, LocalDateTime moment2, TaskStatus taskStatus);
}
