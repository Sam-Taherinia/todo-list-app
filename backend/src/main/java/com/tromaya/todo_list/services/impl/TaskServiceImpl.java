package com.tromaya.todo_list.services.impl;

import com.tromaya.todo_list.domain.entities.Task;
import com.tromaya.todo_list.repositories.TaskRepository;
import com.tromaya.todo_list.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }
}
