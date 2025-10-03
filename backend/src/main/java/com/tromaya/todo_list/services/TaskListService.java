package com.tromaya.todo_list.services;

import com.tromaya.todo_list.domain.entities.TaskList;

import java.util.List;

public interface TaskListService {

    List<TaskList> listTaskLists();
}
