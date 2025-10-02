package com.tromaya.todo_list.domain.dto;

import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count, // number of tasks
        Double progress, // a number between 0 and 1
        List<TaskDto> tasks
) {
}
