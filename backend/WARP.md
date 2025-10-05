# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is the Spring Boot backend for a full-stack ToDo list web application. The backend provides REST API endpoints for managing task lists and tasks, with a React TypeScript frontend that consumes these APIs.

**Technology Stack:**
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA for persistence
- PostgreSQL (production) / H2 (testing)
- JUnit 5 for testing
- Maven for build management

## Architecture

The backend follows a layered Spring Boot architecture with complete CRUD functionality:

### Core Components
- **Main Application**: `com.tromaya.todo_list.TodoListApplication` - Spring Boot entry point
- **Controllers**: REST API endpoints (`TaskListController`, `TasksController`)
- **Services**: Business logic layer (`TaskListService`, `TaskService`)
- **Repositories**: Data access layer (`TaskListRepository`, `TaskRepository`)
- **Entities**: JPA domain models (`TaskList`, `Task`, `TaskPriority`, `TaskStatus`)
- **DTOs**: Data transfer objects for API responses
- **Mappers**: Entity-DTO conversion logic

### Domain Model
- **TaskList**: Container entity with title, description, timestamps, and one-to-many relationship with tasks
- **Task**: Core entity with title, description, due date, priority (HIGH/MEDIUM/LOW), status (OPEN/CLOSED), and many-to-one relationship with TaskList
- **UUID Primary Keys**: All entities use UUID for better scalability and security

## Development Commands

### Build & Run
```powershell
# Build the project
.\mvnw clean compile

# Run the application
.\mvnw spring-boot:run

# Build JAR
.\mvnw clean package

# Run built JAR
java -jar target/todo-list-0.0.1-SNAPSHOT.jar
```

### Testing
```powershell
# Run all tests
.\mvnw test

# Run specific test class
.\mvnw test -Dtest=TodoListApplicationTests

# Run tests with coverage
.\mvnw test jacoco:report
```

### Development Tools
```powershell
# Clean build artifacts
.\mvnw clean

# Verify project dependencies
.\mvnw dependency:tree

# Check for dependency updates
.\mvnw versions:display-dependency-updates

# Format code (if formatter plugin added)
.\mvnw spring-javaformat:apply
```

### Docker Development

```powershell
# Start PostgreSQL database in Docker
docker-compose up -d

# Database will be available on localhost:5432
# Default credentials: postgres/123456 (development only)

# Stop database
docker-compose down
```

## API Endpoints

The backend provides comprehensive REST API endpoints:

### Task Lists API
- `GET /task-lists` - Retrieve all task lists
- `POST /task-lists` - Create a new task list
- `GET /task-lists/{id}` - Get specific task list by UUID
- `PUT /task-lists/{id}` - Update existing task list
- `DELETE /task-lists/{id}` - Delete task list (cascades to tasks)

### Tasks API
- `GET /task-lists/{listId}/tasks` - Get all tasks in a specific list
- `POST /task-lists/{listId}/tasks` - Create new task in list
- `GET /task-lists/{listId}/tasks/{taskId}` - Get specific task
- `PUT /task-lists/{listId}/tasks/{taskId}` - Update existing task
- `DELETE /task-lists/{listId}/tasks/{taskId}` - Delete specific task

## Configuration

- **Application Properties**: `src/main/resources/application.properties`
- **Database**: PostgreSQL for production, H2 for testing
- **Port**: Default Spring Boot port (8080)
- **Frontend Integration**: Configured to work with React frontend on port 5173

## Project Structure

Standard Maven-based Spring Boot project with complete implementation:

```
src/main/java/com/tromaya/todo_list/
├── TodoListApplication.java          # Main Spring Boot class
├── controllers/                      # REST API controllers
│   ├── GlobalExceptionHandler.java   # Global error handling
│   ├── TaskListController.java       # Task list CRUD endpoints
│   └── TasksController.java          # Task CRUD endpoints
├── services/                         # Business logic layer
│   ├── TaskListService.java          # Task list operations
│   ├── TaskService.java              # Task operations
│   └── impl/                         # Service implementations
├── repositories/                     # Data access layer
│   ├── TaskListRepository.java       # JPA repository for TaskList
│   └── TaskRepository.java           # JPA repository for Task
├── domain/
│   ├── entities/                     # JPA entities
│   │   ├── TaskList.java             # Task list entity
│   │   ├── Task.java                 # Task entity
│   │   ├── TaskPriority.java         # Priority enum
│   │   └── TaskStatus.java           # Status enum
│   └── dto/                          # Data transfer objects
│       ├── TaskListDto.java          # Task list DTO
│       ├── TaskDto.java              # Task DTO
│       └── ErrorResponse.java        # Error response DTO
└── mappers/                          # Entity-DTO conversion
    ├── TaskListMapper.java           # Task list mapping
    ├── TaskMapper.java               # Task mapping
    └── impl/                         # Mapper implementations
```
