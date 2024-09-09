package ru.test.taskmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.test.taskmanager.model.TaskStatus;

@Getter
@Setter
@Builder
public class TaskStatusDto {
    Integer id;
    TaskStatus taskStatus;
}
