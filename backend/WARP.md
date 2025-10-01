# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is a ToDo list web application with a Spring Boot backend (and a planned Node.js frontend & API component). The project follows standard Spring Boot conventions and uses Maven for build management.

**Technology Stack:**
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA for persistence
- PostgreSQL (production) / H2 (testing)
- JUnit 5 for testing
- Maven for build management

## Architecture

The project currently contains a minimal Spring Boot application structure:

- **Main Application**: `com.tromaya.todo_list.TodoListApplication` - The main Spring Boot entry point
- **Package Structure**: `com.tromaya.todo_list.*` - Standard package organization
- **Database Support**: Configured for PostgreSQL in production and H2 for testing
- **Testing**: JUnit 5 based testing framework

The codebase is in early stages with just the basic Spring Boot application skeleton. Additional layers (controllers, services, repositories, entities) will need to be developed for the todo functionality.

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

## Configuration

- **Application Properties**: `src/main/resources/application.properties`
- **Database**: PostgreSQL for runtime, H2 for testing
- **Port**: Default Spring Boot port (8080) unless configured otherwise

## Project Structure Notes

This is a standard Maven-based Spring Boot project following conventional directory structure:
- `src/main/java/` - Main application code
- `src/main/resources/` - Configuration files and static resources  
- `src/test/java/` - Test classes
- `pom.xml` - Maven configuration and dependencies

The project appears to be in its initial setup phase and will likely need additional components like:
- Entity models for todo items
- Repository interfaces for data access
- Service layer for business logic
- REST controllers for API endpoints
- Frontend integration (as mentioned in README)
