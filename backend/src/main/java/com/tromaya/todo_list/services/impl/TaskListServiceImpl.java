package com.tromaya.todo_list.services.impl;

import com.tromaya.todo_list.domain.entities.TaskList;
import com.tromaya.todo_list.repositories.TaskListRepository;
import com.tromaya.todo_list.services.TaskListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }
}
