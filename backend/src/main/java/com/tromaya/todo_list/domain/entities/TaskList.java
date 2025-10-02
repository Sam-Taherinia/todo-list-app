package com.tromaya.todo_list.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "task_lists")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //when the id is null, JPA generates UUID for us
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updatedDate;

}
