package com.tromaya.todo_list.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "task_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //when the id is null, JPA generates UUID for us
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "taskList", cascade = {
            CascadeType.REMOVE, CascadeType.PERSIST // CascadeType.REMOVE: when we delete a tasklist all the tasks will be deleted too
                                                    // CascadeType.PERSIST: when we save a tasklist any new task it contains will be saved too
    }) // one task-list to many tasks
    private List<Task> tasks;

    @Column(name = "created", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updatedDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id) && Objects.equals(title, taskList.title) && Objects.equals(description, taskList.description) && Objects.equals(tasks, taskList.tasks) && Objects.equals(createdDate, taskList.createdDate) && Objects.equals(updatedDate, taskList.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tasks, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
