package com.tromaya.todo_list.domain.dto;

// a Java record is a special kind of class
// whose purpose is to simplify the creation
// of data carrier classes by automatically
// generating much of the usual boilerplate code.

import com.tromaya.todo_list.domain.entities.TaskPriority;
import com.tromaya.todo_list.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

// so I used a java record to provides a concise way to
// create an "immutable data class" (once you create it,
// you cannot change its fields. the components
// are implicitly final and there are no setters.)
// with all the boilerplate code we need
public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
