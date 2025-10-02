package com.tromaya.todo_list.mappers;

import com.tromaya.todo_list.domain.dto.TaskDto;
import com.tromaya.todo_list.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);

}
