# Design Patterns Analysis - Todo List Backend

This document identifies and explains the design patterns used in the todo-list backend application. The project is built using Spring Boot and follows a layered architecture with various design patterns across Structural, Behavioral, and Creational categories.

---

## Table of Contents
1. [Structural Patterns](#structural-patterns)
2. [Behavioral Patterns](#behavioral-patterns)
3. [Creational Patterns](#creational-patterns)
4. [Architectural Patterns](#architectural-patterns)

---

## Structural Patterns

### 1. **Facade Pattern**

**Location**: Service Layer (`TaskService`, `TaskListService`)

**Description**: The service layer acts as a facade that provides a simplified interface to the complex subsystem of repositories, mappers, and entities. It hides the complexity of data access and business logic from the controllers.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\TaskService.java start=1
package com.tromaya.todo_list.services;

import com.tromaya.todo_list.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId, Task task);
    void deleteTask(UUID taskListId, UUID taskId);
}
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\impl\TaskServiceImpl.java start=35
@Transactional
@Override
public Task createTask(UUID taskListId, Task task) {
    if (null != task.getId()) {
        throw new IllegalArgumentException("Task already has an ID!");
    }
    if (null == task.getTitle() || task.getTitle().isBlank()) {
        throw new IllegalArgumentException("Task title is empty!");
    }
    TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
            .orElse(TaskPriority.MEDIUM);

    TaskStatus taskStatus = TaskStatus.OPEN;

    TaskList taskList = taskListRepository.findById(taskListId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ToDo List ID!"));

    LocalDateTime now = LocalDateTime.now();
    Task taskToSave = new Task(
            null,
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            taskStatus,
            taskPriority,
            taskList,
            now,
            now
    );
    return taskRepository.save(taskToSave);
}
```

**Why it's a Facade**: The service simplifies complex operations by coordinating between repositories, validation logic, and entity management.

---

### 2. **Adapter Pattern** (Mapper Pattern)

**Location**: `TaskMapper`, `TaskListMapper` and their implementations

**Description**: Mappers adapt between two incompatible interfaces - DTOs (Data Transfer Objects) used for API communication and Entities used for database persistence. They convert data from one form to another.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\mappers\TaskMapper.java start=1
package com.tromaya.todo_list.mappers;

import com.tromaya.todo_list.domain.dto.TaskDto;
import com.tromaya.todo_list.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);

}
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\mappers\impl\TaskMapperImpl.java start=8
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}
```

**Why it's an Adapter**: It converts between two incompatible interfaces (Entity ↔ DTO), allowing the controller layer to work with DTOs while the database layer works with Entities.

---

### 3. **Proxy Pattern** (via Spring Data JPA)

**Location**: Repository Layer (`TaskRepository`, `TaskListRepository`)

**Description**: Spring Data JPA creates proxy implementations of repository interfaces at runtime. These proxies handle database operations without requiring explicit implementation code.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\repositories\TaskRepository.java start=1
package com.tromaya.todo_list.repositories;

import com.tromaya.todo_list.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    // naming of these methods are really important for the SpringDataJPA to work out
    // the queries for us.
    List<Task> findByTaskListId(UUID taskListId);
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID id);
    void deleteByTaskListIdAndId(UUID taskListId, UUID id);
}
```

**Why it's a Proxy**: Spring creates a proxy object that implements the interface and adds database access logic at runtime, acting as a placeholder/proxy for the actual database operations.

---

### 4. **Composite Pattern**

**Location**: Entity relationships (`TaskList` and `Task`)

**Description**: A TaskList contains a collection of Task objects, forming a tree-like structure where TaskList acts as a composite that can contain multiple Task objects (leaves).

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\domain\entities\TaskList.java start=30
@OneToMany(mappedBy = "taskList", cascade = {
        CascadeType.REMOVE, CascadeType.PERSIST // CascadeType.REMOVE: when we delete a tasklist all the tasks will be deleted too
                                                // CascadeType.PERSIST: when we save a tasklist any new task it contains will be saved too
}) // one task-list to many tasks
private List<Task> tasks;
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\domain\entities\Task.java start=39
@ManyToOne(fetch = FetchType.LAZY) // the task-list won't be loaded from the database until it's needed
@JoinColumn(name = "task_list_id") // this specifies the foreign key column name in the tasks table.
private TaskList taskList;
```

**Why it's Composite**: TaskList is a composite that manages a collection of Task objects, treating individual tasks and the collection uniformly.

---

## Behavioral Patterns

### 1. **Strategy Pattern**

**Location**: Service implementations (`TaskServiceImpl`, `TaskListServiceImpl`)

**Description**: Different service implementations can be swapped based on business requirements. The interface defines the contract while implementations provide the strategy.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\TaskListService.java start=1
package com.tromaya.todo_list.services;

import com.tromaya.todo_list.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList(UUID TaskListId, TaskList taskList);
    void deleteTaskList(UUID TaskListId);
}
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\impl\TaskListServiceImpl.java start=15
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
    
    // ... other strategy implementations
}
```

**Why it's Strategy**: The service interface defines a family of algorithms (business operations), and implementations can be swapped without affecting the controller layer.

---

### 2. **Template Method Pattern** (via Spring Boot Lifecycle)

**Location**: Main Application Class

**Description**: Spring Boot's `SpringApplication.run()` follows the Template Method pattern, defining the skeleton of application initialization while allowing specific steps to be customized.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\TodoListApplication.java start=1
package com.tromaya.todo_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListApplication {

	public static void main(String[] args) {

        SpringApplication.run(TodoListApplication.class, args);

	}

}
```

**Why it's Template Method**: Spring Boot defines a template for application startup, and `@SpringBootApplication` allows customization of specific initialization steps.

---

### 3. **Observer Pattern** (via Spring Events and JPA)

**Location**: Entity lifecycle with `@Transactional` and cascade operations

**Description**: JPA observes entity state changes and triggers cascading operations. When a TaskList is deleted, all associated Tasks are automatically notified and deleted.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\domain\entities\TaskList.java start=30
@OneToMany(mappedBy = "taskList", cascade = {
        CascadeType.REMOVE, CascadeType.PERSIST
})
private List<Task> tasks;
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\impl\TaskServiceImpl.java start=100
@Transactional // it insures the delete operation happens within a database transaction
// so if any part of the delete operation fails, then the entire operation rolls back.
// that is to maintain database consistency even if errors occur
@Override
public void deleteTask(UUID taskListId, UUID taskId) {
    taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
}
```

**Why it's Observer**: The cascade operations observe changes to the parent entity and automatically propagate those changes to child entities.

---

### 4. **Chain of Responsibility Pattern**

**Location**: Exception Handling (`GlobalExceptionHandler`)

**Description**: Exception handling is delegated through a chain. If a controller throws an exception, it's caught by the global exception handler which processes it appropriately.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\controllers\GlobalExceptionHandler.java start=1
package com.tromaya.todo_list.controllers;

import com.tromaya.todo_list.domain.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice // this tells spring that this class handles exceptions across all our controllers
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleExceptions(
            RuntimeException ex, WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
```

**Why it's Chain of Responsibility**: Exceptions are passed through a chain of handlers until one handles it. The `@ControllerAdvice` intercepts exceptions from all controllers.

---

## Creational Patterns

### 1. **Dependency Injection (DI) / Inversion of Control (IoC)**

**Location**: Throughout all components via Spring's `@Autowired` and constructor injection

**Description**: Spring creates and manages object instances, injecting dependencies where needed. This is a form of the Factory pattern where Spring acts as the object factory.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\controllers\TaskListController.java start=17
private final TaskListService taskListService;
private final TaskListMapper taskListMapper;

public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
    this.taskListService = taskListService;
    this.taskListMapper = taskListMapper;
}
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\impl\TaskServiceImpl.java start=22
private final TaskRepository taskRepository;
private final TaskListRepository taskListRepository;

public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
    this.taskRepository = taskRepository;
    this.taskListRepository = taskListRepository;
}
```

**Why it's a Creational Pattern**: Spring manages object creation and lifecycle, injecting dependencies through constructors (factory-like behavior).

---

### 2. **Builder Pattern** (via Java Records and Lombok)

**Location**: DTOs using Java Records and Entities using Lombok

**Description**: Java Records provide an implicit builder-like construction mechanism. Lombok's `@AllArgsConstructor` generates constructors that act like builders.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\domain\dto\TaskDto.java start=19
public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\domain\entities\Task.java start=11
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    // ... fields
}
```

**Why it's Builder-like**: Records and Lombok annotations simplify object construction with multiple parameters, similar to the Builder pattern's intent.

---

### 3. **Factory Pattern** (via Spring Data JPA)

**Location**: Repository instantiation and entity ID generation

**Description**: Spring Data JPA acts as a factory for repository implementations. Additionally, `@GeneratedValue` uses a factory to generate UUIDs for entities.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\domain\entities\Task.java start=19
@Id
@GeneratedValue(strategy = GenerationType.UUID) //when the id is null, JPA generates UUID for us
@Column(name = "id", updatable = false, nullable = false)
private UUID id;
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\repositories\TaskListRepository.java start=9
// extends JpaRepository gives us all the CRUD behavior for the chosen entity and also has Query
// and Paging behavior
@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
}
// SpringDataJPA will do the rest: it does the implementation for us, making available methods like
// Save, findByID, findAll, DeleteByID and...
```

**Why it's Factory**: Spring Data creates repository implementations at runtime, and JPA uses factories to generate entity IDs and create entity instances.

---

### 4. **Singleton Pattern** (via Spring Beans)

**Location**: All `@Service`, `@Repository`, `@Component`, and `@Controller` annotated classes

**Description**: Spring manages beans as singletons by default. Each service, repository, and controller is instantiated once and reused throughout the application lifecycle.

**Example Code**:

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\services\impl\TaskListServiceImpl.java start=15
@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }
    // ...
}
```

```java path=C:\Users\Sam\Desktop\Programming\projects\todo-list\backend\src\main\java\com\tromaya\todo_list\mappers\impl\TaskMapperImpl.java start=8
@Component
public class TaskMapperImpl implements TaskMapper {
    // ...
}
```

**Why it's Singleton**: Spring ensures only one instance of each bean exists in the application context, following the Singleton pattern.

---

## Architectural Patterns

### 1. **Layered Architecture (MVC)**

The application follows a clear layered architecture:

- **Controller Layer**: REST endpoints (`TaskListController`, `TasksController`)
- **Service Layer**: Business logic (`TaskService`, `TaskListService`)
- **Repository Layer**: Data access (`TaskRepository`, `TaskListRepository`)
- **Domain Layer**: Entities and DTOs

**Visualization**:
```
Controller (REST API) 
    ↓ 
Service (Business Logic) 
    ↓ 
Repository (Data Access) 
    ↓ 
Database (PostgreSQL/H2)
```

---

### 2. **Repository Pattern**

**Location**: `TaskRepository`, `TaskListRepository`

The repository pattern abstracts data access logic, providing a collection-like interface to access domain objects.

---

### 3. **Data Transfer Object (DTO) Pattern**

**Location**: `TaskDto`, `TaskListDto`, `ErrorResponse`

DTOs transfer data between layers, decoupling API contracts from database entities.

---

## Summary Table

| Pattern | Category | Location | Purpose |
|---------|----------|----------|---------|
| **Facade** | Structural | Service Layer | Simplifies complex subsystems |
| **Adapter (Mapper)** | Structural | Mapper classes | Converts between DTOs and Entities |
| **Proxy** | Structural | Spring Data JPA Repositories | Runtime database access proxies |
| **Composite** | Structural | TaskList-Task relationship | Tree structure for aggregation |
| **Strategy** | Behavioral | Service implementations | Interchangeable business logic |
| **Template Method** | Behavioral | Spring Boot lifecycle | Application initialization template |
| **Observer** | Behavioral | JPA cascade operations | Entity change propagation |
| **Chain of Responsibility** | Behavioral | Exception handling | Exception processing chain |
| **Dependency Injection** | Creational | Spring IoC Container | Object creation and management |
| **Builder** | Creational | Records and Lombok | Simplified object construction |
| **Factory** | Creational | Spring Data JPA | Repository and ID generation |
| **Singleton** | Creational | Spring Beans | Single instance management |

---

## Conclusion

This todo-list backend application demonstrates a rich use of design patterns:

- **Structural patterns** help organize code into clean layers with clear responsibilities
- **Behavioral patterns** manage interactions between objects and handle cross-cutting concerns
- **Creational patterns** simplify object creation and dependency management

The Spring Framework provides many of these patterns out-of-the-box, allowing developers to focus on business logic rather than boilerplate infrastructure code. This results in maintainable, testable, and extensible code that follows SOLID principles.
