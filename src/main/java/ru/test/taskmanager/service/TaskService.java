package ru.test.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.taskmanager.dto.TaskStatusDto;
import ru.test.taskmanager.exceptions.NotFoundException;
import ru.test.taskmanager.model.Task;
import ru.test.taskmanager.model.TaskStatus;
import ru.test.taskmanager.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        task.setTaskStatus(TaskStatus.NEW);
        return taskRepository.save(task);
    }

    public Task patchTask(Integer taskId, Task task) {
        Task updatedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task id:" + taskId + " was not found in the repository"));

        if(task.getId() != null) {
            updatedTask.setId(task.getId());
        }
        if(task.getName() != null) {
            updatedTask.setName(task.getName());
        }
        if(task.getDescription() != null) {
            updatedTask.setDescription(task.getDescription());
        }
        if(task.getDuration() != null) {
            updatedTask.setDuration(task.getDuration());
        }
        if(task.getExecutionTime() != null) {
            updatedTask.setExecutionTime(task.getExecutionTime());
        }
        if(task.getTaskStatus() != null) {
            updatedTask.setTaskStatus(task.getTaskStatus());
        }
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task getById(Integer taskId) {
        Optional<Task> taskFromRepo = taskRepository.findById(taskId);
        if (taskFromRepo.isEmpty()){
            throw new NotFoundException("task id:" + taskId + " was not found in the repository");
        }
        return taskFromRepo.get();
    }

    public List<Task> getTodayTasks(String status) {
        LocalDateTime from = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime till = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return getTaskFromRepositoryByPeriod(from, till, status);
    }

    public List<Task> getWeekTasks(String status) {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime till = LocalDateTime.now().plusDays(7L);
        return getTaskFromRepositoryByPeriod(from, till, status);
    }

    public List<Task> getMonthTasks(String status) {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime till = LocalDateTime.now().plusMonths(1L);
        return getTaskFromRepositoryByPeriod(from, till, status);
    }

    List<Task> getTaskFromRepositoryByPeriod(LocalDateTime from, LocalDateTime till, String status) {
        if(status == null) {
            return taskRepository.findByExecutionTimeBetween(from, till);
        }
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.findByExecutionTimeBetweenAndTaskStatus(from, till, taskStatus);
    }

    public Task updateTaskStatus(TaskStatusDto taskStatusDto) {
        Task task = taskRepository.findById(taskStatusDto.getId())
                .orElseThrow(() -> new NotFoundException("no task for updating with id " + taskStatusDto.getId()));
        task.setTaskStatus(taskStatusDto.getTaskStatus());
        return taskRepository.save(task);
    }
}

