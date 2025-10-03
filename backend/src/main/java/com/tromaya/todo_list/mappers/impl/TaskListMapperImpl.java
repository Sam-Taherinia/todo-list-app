package com.tromaya.todo_list.mappers.impl;

import com.tromaya.todo_list.domain.dto.TaskListDto;
import com.tromaya.todo_list.domain.entities.Task;
import com.tromaya.todo_list.domain.entities.TaskList;
import com.tromaya.todo_list.domain.entities.TaskStatus;
import com.tromaya.todo_list.mappers.TaskListMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapperImpl taskMapper;

    public TaskListMapperImpl(TaskMapperImpl taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks()).map(List::size).orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto)
                                .toList()
                        ).orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks){

        if (null == tasks || tasks.isEmpty()) {
            return null;
        }
        long closedTaskCount = tasks.stream().filter(
                task -> TaskStatus.CLOSED == task.getStatus()).count();

        return (double) closedTaskCount/tasks.size();

    }
}
