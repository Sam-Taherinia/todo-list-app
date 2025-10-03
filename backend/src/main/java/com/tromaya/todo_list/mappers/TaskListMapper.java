package com.tromaya.todo_list.mappers;

import com.tromaya.todo_list.domain.dto.TaskListDto;
import com.tromaya.todo_list.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);

}
