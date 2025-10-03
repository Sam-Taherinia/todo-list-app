package com.tromaya.todo_list.repositories;

import com.tromaya.todo_list.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// extends JpaRepository gives us all the CRUD behavior for the chosen entity and also has Query
// and Paging behavior
@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
}
// SpringDaraJPA will do the rest: it does the implementation for us, making available methods like
// Save, findByID, findAll, DeleteByID and...