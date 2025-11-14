# SOLID Design Principles Analysis

This document identifies and explains the SOLID design principles implemented in the Todo List application.

## Overview

The project demonstrates **ALL FIVE** SOLID principles:

1. ✅ **Single Responsibility Principle (SRP)**
2. ✅ **Open/Closed Principle (OCP)**
3. ✅ **Liskov Substitution Principle (LSP)**
4. ✅ **Interface Segregation Principle (ISP)**
5. ✅ **Dependency Inversion Principle (DIP)**

---

## 1. Single Responsibility Principle (SRP)

> *A class should have only one reason to change.*

The project demonstrates excellent separation of concerns with distinct layers, each having a single responsibility.

### Examples:

#### Controllers - Handle HTTP requests only
```java
@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TasksController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
```
**Responsibility:** HTTP request handling, path mapping, and response formatting.

#### Services - Business logic only
```java
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an ID!");
        }
        // Business logic for creating tasks
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ToDo List ID!"));
        
        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                TaskStatus.OPEN,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }
}
```
**Responsibility:** Business logic, validation, and transaction management.

#### Repositories - Data access only
```java
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByTaskListId(UUID taskListId);
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID id);
    void deleteByTaskListIdAndId(UUID taskListId, UUID id);
}
```
**Responsibility:** Database operations and query execution.

#### Mappers - Data transformation only
```java
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
**Responsibility:** Converting between Entity and DTO objects.

#### Exception Handler - Error handling only
```java
@ControllerAdvice
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
**Responsibility:** Global exception handling and error response formatting.

---

## 2. Open/Closed Principle (OCP)

> *Software entities should be open for extension but closed for modification.*

The project uses interfaces and Spring's dependency injection to allow extension without modifying existing code.

### Examples:

#### Service Interfaces
```java
public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId, Task task);
    void deleteTask(UUID taskListId, UUID taskId);
}
```

**How it's extensible:**
- You can create new implementations (e.g., `CachedTaskServiceImpl`, `AsyncTaskServiceImpl`) without modifying the interface or existing implementations
- Controllers depend on the interface, not the concrete implementation
- New service implementations can add logging, caching, or other cross-cutting concerns

#### Mapper Interfaces
```java
public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
```

**How it's extensible:**
- New mapper implementations can be added for different serialization formats (e.g., XML, CSV)
- The controller doesn't need to change when a new mapper is added

#### Repository Extension
```java
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByTaskListId(UUID taskListId);
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID id);
    void deleteByTaskListIdAndId(UUID taskListId, UUID id);
}
```

**How it's extensible:**
- Extends `JpaRepository` to inherit CRUD operations
- Custom query methods can be added without modifying the base repository
- Can switch to a different implementation (e.g., MongoDB repository) by implementing the same interface

---

## 3. Liskov Substitution Principle (LSP)

> *Objects of a superclass should be replaceable with objects of a subclass without breaking the application.*

The implementation classes can be substituted for their interfaces without any issues.

### Examples:

#### Service Substitution
```java
// Interface
public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    // ... other methods
}

// Implementation
@Service
public class TaskServiceImpl implements TaskService {
    // Correct implementation that fulfills the contract
    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }
}

// Controller depends on interface
public class TasksController {
    private final TaskService taskService; // Can be ANY TaskService implementation
    
    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService; // LSP: Any TaskService works
        this.taskMapper = taskMapper;
    }
}
```

**Why LSP is satisfied:**
- `TaskServiceImpl` fulfills the contract defined by `TaskService`
- Any other implementation of `TaskService` can be injected without breaking the controller
- The controller doesn't know or care about the concrete implementation

#### Repository Substitution
```java
@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
    // Extends JpaRepository - can be substituted with any JPA implementation
}

// Service uses the interface
@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;
    
    // Works with any JpaRepository<TaskList, UUID> implementation
    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }
}
```

---

## 4. Interface Segregation Principle (ISP)

> *No client should be forced to depend on methods it does not use.*

The project defines focused interfaces that clients actually need, rather than large, bloated interfaces.

### Examples:

#### Focused Service Interfaces

**TaskService** - Only task operations:
```java
public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId, Task task);
    void deleteTask(UUID taskListId, UUID taskId);
}
```

**TaskListService** - Only task list operations:
```java
public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList(UUID TaskListId, TaskList taskList);
    void deleteTaskList(UUID TaskListId);
}
```

**Why ISP is satisfied:**
- Services are separated by domain concern (Task vs TaskList)
- Controllers only depend on the service they need
- `TasksController` doesn't have access to `TaskListService` methods it doesn't need
- No bloated "super service" interface forcing clients to depend on unused methods

#### Focused Mapper Interfaces

**TaskMapper** - Only task mapping:
```java
public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
```

**TaskListMapper** - Only task list mapping:
```java
public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
```

**Why ISP is satisfied:**
- Each mapper has only two methods related to its specific entity
- Controllers inject only the mapper they need
- No "universal mapper" forcing all clients to see all mapping methods

---

## 5. Dependency Inversion Principle (DIP)

> *High-level modules should not depend on low-level modules. Both should depend on abstractions.*

The project uses dependency injection and interfaces throughout, ensuring that high-level components depend on abstractions, not concrete implementations.

### Examples:

#### Controller → Service Dependency
```java
@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TasksController {
    // Depends on ABSTRACTIONS (interfaces), not concrete classes
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    // Spring injects the concrete implementations at runtime
    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        // Uses the abstraction, not knowing the concrete implementation
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
```

**Why DIP is satisfied:**
- `TasksController` (high-level) depends on `TaskService` interface (abstraction)
- `TasksController` doesn't know about `TaskServiceImpl` (low-level implementation)
- Implementations can be swapped without changing the controller

#### Service → Repository Dependency
```java
@Service
public class TaskServiceImpl implements TaskService {
    // Depends on ABSTRACTIONS (repository interfaces)
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    // Constructor injection - Spring provides concrete implementations
    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        // Uses abstraction - doesn't care about JPA implementation details
        return taskRepository.findByTaskListId(taskListId);
    }
}
```

**Why DIP is satisfied:**
- `TaskServiceImpl` (high-level business logic) depends on `TaskRepository` interface (abstraction)
- The service doesn't depend on JPA-specific implementation details
- Repository implementation can be changed (e.g., from JPA to MongoDB) without affecting the service

#### Dependency Flow Diagram
```
High-level:    Controller ────depends on────> TaskService (interface)
                                                      ▲
                                                      │ implements
Low-level:                                    TaskServiceImpl
                                                      │
                                                      │ depends on
                                                      ▼
High-level:                                   TaskRepository (interface)
                                                      ▲
                                                      │ implements
Low-level:                                    JpaRepository (Spring Data)
```

All arrows point toward abstractions (interfaces), not concrete implementations.

---

## Summary

This Todo List application demonstrates **all five SOLID principles**:

| Principle | Implementation | Location |
|-----------|---------------|----------|
| **SRP** | Separation of concerns across layers | Controllers, Services, Repositories, Mappers, Exception Handlers |
| **OCP** | Interface-based design allowing extension | Service interfaces, Mapper interfaces, Repository interfaces |
| **LSP** | Implementation classes properly substitute their interfaces | All service and mapper implementations |
| **ISP** | Focused, client-specific interfaces | Separate TaskService and TaskListService interfaces |
| **DIP** | Dependency on abstractions via constructor injection | Controllers depend on services, Services depend on repositories |

The architecture follows a clean, layered approach that makes the codebase:
- **Maintainable**: Easy to understand and modify
- **Testable**: Dependencies can be mocked via interfaces
- **Extensible**: New features can be added without breaking existing code
- **Flexible**: Implementations can be swapped without affecting clients
