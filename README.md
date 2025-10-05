# ToDo List Web Application

A complete, production-ready full-stack ToDo list application built with React TypeScript frontend and Spring Boot backend. This application provides comprehensive task and task list management with modern UI components, robust data persistence, and RESTful API architecture.

![Project Status](https://img.shields.io/badge/Status-Production%20Ready-brightgreen)
![Frontend](https://img.shields.io/badge/Frontend-React%2018%20%2B%20TypeScript-blue)
![Backend](https://img.shields.io/badge/Backend-Spring%20Boot%203.5.6-green)
![Database](https://img.shields.io/badge/Database-PostgreSQL-blue)

## 🏗️ Architecture Overview

This project follows a clean separation between frontend and backend:

```
todo-list/
├── app-ui/          # React TypeScript Frontend
├── backend/         # Spring Boot Backend API
└── README.md        # This file
```

### Frontend (`app-ui/`)
- **Framework**: React 18 with TypeScript
- **Build Tool**: Vite for fast development and optimized builds
- **UI Library**: NextUI components with Tailwind CSS styling
- **Routing**: React Router DOM for client-side navigation
- **State Management**: React Context API with useReducer
- **HTTP Client**: Axios for API communication
- **Animations**: Framer Motion for smooth transitions
- **Icons**: Lucide React

### Backend (`backend/`)
- **Framework**: Spring Boot 3.5.6 with Java 21
- **Database**: PostgreSQL (production) / H2 (testing)
- **ORM**: Spring Data JPA with Hibernate
- **Architecture**: Layered architecture (Controllers → Services → Repositories → Entities)
- **Build Tool**: Maven
- **API**: RESTful endpoints with JSON responses

## 🚀 Features

### ✅ Completed Core Functionality

#### Task List Management
- **Full CRUD Operations**: Create, read, update, and delete task lists
- **Rich Metadata**: Title, description, creation/update timestamps
- **UUID Identification**: Secure, scalable unique identifiers
- **Cascade Operations**: Deleting a list removes all associated tasks

#### Task Management
- **Complete Task CRUD**: Create, read, update, and delete tasks within lists
- **Priority System**: HIGH, MEDIUM, LOW priority levels with visual indicators
- **Status Tracking**: OPEN and CLOSED states with easy toggle
- **Due Date Management**: Optional due date assignment and tracking
- **Rich Task Data**: Title, description, timestamps, and relationships

#### User Interface
- **Modern Design**: NextUI components with custom primary color (#FFB703)
- **Responsive Layout**: Mobile-first design that works on all devices
- **Smooth Animations**: Framer Motion powered transitions and interactions
- **Dark Mode**: Consistent dark theme throughout the application
- **Intuitive Navigation**: React Router based routing between views
- **Real-time Updates**: Automatic state synchronization with backend

#### Technical Features
- **Type Safety**: Full TypeScript implementation across frontend
- **Global State Management**: React Context with useReducer pattern
- **API Integration**: Axios-based HTTP client with automatic error handling
- **Form Validation**: Comprehensive input validation and error feedback
- **Component Architecture**: Reusable, maintainable component structure

## 🛠️ Technology Stack

### Frontend Technologies
- **React 18**: Latest React with modern hooks and features
- **TypeScript**: Type-safe JavaScript for better development experience
- **Vite**: Fast build tool and development server with HMR
- **NextUI**: Modern React UI component library
- **Tailwind CSS**: Utility-first CSS framework
- **Framer Motion**: Animation library for React
- **React Router DOM**: Client-side routing
- **Axios**: Promise-based HTTP client

### Backend Technologies
- **Java 21**: Latest LTS version of Java
- **Spring Boot 3.5.6**: Production-ready Spring framework
- **Spring Data JPA**: Data access layer with Hibernate
- **PostgreSQL**: Robust relational database
- **H2 Database**: In-memory database for testing
- **Maven**: Dependency management and build automation
- **JUnit 5**: Modern testing framework

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** or higher
- **Node.js 22** or higher
- **PostgreSQL** (for production database)
- **Git** (for version control)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd todo-list
```

### 2. Backend Setup

```bash
cd backend

# Install dependencies and compile
./mvnw clean compile

# Start PostgreSQL database (using Docker Compose)
docker-compose up -d

# Run the Spring Boot application
./mvnw spring-boot:run
```

The backend server will start on `http://localhost:8080`

### 3. Frontend Setup

```bash
cd ../app-ui

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will be available at `http://localhost:5173`

## 🐳 Docker Development

Both frontend and backend include Docker configurations for containerized development.

### Frontend Docker

```bash
cd app-ui
docker-compose up --build
```

### Backend Database Docker

```bash
cd backend
docker-compose up -d
```

## 📚 API Documentation

The backend provides RESTful API endpoints:

### Task Lists Endpoints
- `GET /task-lists` - Retrieve all task lists
- `POST /task-lists` - Create a new task list
- `GET /task-lists/{id}` - Get a specific task list
- `PUT /task-lists/{id}` - Update a task list
- `DELETE /task-lists/{id}` - Delete a task list

### Tasks Endpoints
- `GET /task-lists/{listId}/tasks` - Get all tasks in a list
- `POST /task-lists/{listId}/tasks` - Create a new task
- `GET /task-lists/{listId}/tasks/{taskId}` - Get a specific task
- `PUT /task-lists/{listId}/tasks/{taskId}` - Update a task
- `DELETE /task-lists/{listId}/tasks/{taskId}` - Delete a task

## 🗺️ Complete Project Structure

### Frontend Structure (`app-ui/`)
```
app-ui/
├── src/
│   ├── components/                    # React UI Components
│   │   ├── TaskListsScreen.tsx       # Main dashboard - task lists grid
│   │   ├── TasksScreen.tsx           # Task list detail view
│   │   ├── CreateUpdateTaskListScreen.tsx # Task list form
│   │   └── CreateUpdateTaskScreen.tsx     # Task form
│   ├── domain/                       # TypeScript Domain Models
│   │   ├── Task.ts                   # Task interface
│   │   ├── TaskList.ts               # TaskList interface
│   │   ├── TaskPriority.ts           # Priority enum
│   │   └── TaskStatus.ts             # Status enum
│   ├── App.tsx                       # Main routing component
│   ├── AppProvider.tsx               # Global state context
│   ├── main.tsx                      # Application entry point
│   ├── App.css                       # Global styles
│   └── index.css                     # Base CSS imports
├── public/
│   └── list.png                      # App favicon
├── tailwind.config.js                # Tailwind + NextUI configuration
├── vite.config.ts                    # Vite build configuration
├── package.json                      # Dependencies and scripts
├── Dockerfile                        # Container configuration
├── docker-compose.yml                # Development container
└── WARP.md                           # Frontend development guide
```

### Backend Structure (`backend/`)
```
backend/
├── src/main/java/com/tromaya/todo_list/
│   ├── TodoListApplication.java      # Spring Boot main class
│   ├── controllers/                  # REST API Controllers
│   │   ├── TaskListController.java   # Task list CRUD endpoints
│   │   ├── TasksController.java      # Task CRUD endpoints
│   │   └── GlobalExceptionHandler.java # Error handling
│   ├── services/                     # Business Logic Layer
│   │   ├── TaskListService.java      # Task list operations
│   │   ├── TaskService.java          # Task operations
│   │   └── impl/                     # Service implementations
│   ├── repositories/                 # Data Access Layer
│   │   ├── TaskListRepository.java   # TaskList JPA repository
│   │   └── TaskRepository.java       # Task JPA repository
│   ├── domain/
│   │   ├── entities/                 # JPA Entity Models
│   │   │   ├── TaskList.java         # TaskList entity
│   │   │   ├── Task.java             # Task entity
│   │   │   ├── TaskPriority.java     # Priority enum
│   │   │   └── TaskStatus.java       # Status enum
│   │   └── dto/                      # Data Transfer Objects
│   │       ├── TaskListDto.java      # TaskList DTO
│   │       ├── TaskDto.java          # Task DTO
│   │       └── ErrorResponse.java    # Error response DTO
│   └── mappers/                      # Entity-DTO Conversion
│       ├── TaskListMapper.java       # TaskList mapping
│       ├── TaskMapper.java           # Task mapping
│       └── impl/                     # Mapper implementations
├── src/main/resources/
│   └── application.properties        # Spring Boot configuration
├── src/test/java/                    # Test classes
├── pom.xml                           # Maven configuration
├── docker-compose.yml                # PostgreSQL container
└── WARP.md                           # Backend development guide
```

## 🧪 Testing

### Backend Testing
```bash
cd backend

# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=TodoListApplicationTests
```

### Frontend Testing
```bash
cd app-ui

# Run tests (when configured)
npm test

# Run linting
npm run lint
```

## 🏗️ Build for Production

### Backend
```bash
cd backend

# Build JAR file
./mvnw clean package

# Run built JAR
java -jar target/todo-list-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd app-ui

# Build for production
npm run build

# Preview production build
npm run preview
```

## 🔧 Development Tips & Best Practices

### Database Configuration
- **PostgreSQL**: Primary database for production with full ACID compliance
- **H2 Database**: In-memory database for fast testing cycles
- **Connection**: Configure via `src/main/resources/application.properties`
- **Docker**: Use `docker-compose.yml` in backend folder for local PostgreSQL
- **UUID Keys**: All entities use UUID for better scalability and security

### Frontend Architecture
- **Vite Proxy**: Development server automatically proxies `/api/*` to `localhost:8080`
- **Hot Reload**: Instant updates during development with HMR
- **Type Safety**: Full TypeScript coverage with strict type checking
- **Component Pattern**: Reusable, composable React components
- **Custom Theme**: NextUI theme customized with primary color #FFB703

### State Management Strategy
- **Global State**: React Context + useReducer for application state
- **API Integration**: Axios interceptors handle requests/responses automatically
- **Cache Strategy**: Tasks organized by taskListId for optimal performance
- **Error Handling**: Comprehensive error boundaries and user feedback
- **Real-time Sync**: State automatically updates after successful API calls

### Code Quality
- **ESLint**: Configured with React and TypeScript rules
- **Prettier**: Code formatting (when configured)
- **Maven**: Backend dependency management and build lifecycle
- **Testing**: JUnit 5 for backend, frontend testing framework ready

## 🚀 Production Deployment

### Backend Deployment
```bash
# Build production JAR
cd backend
./mvnw clean package -DskipTests

# Run with production profile
java -jar -Dspring.profiles.active=prod target/todo-list-0.0.1-SNAPSHOT.jar
```

### Frontend Deployment
```bash
# Build production assets
cd app-ui
npm run build

# Serve with any static file server
# Files will be in ./dist directory
```

### Environment Variables
```bash
# Backend production configuration
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/todolist
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password

# Frontend API endpoint (if different from localhost:8080)
export VITE_API_BASE_URL=https://your-api-domain.com
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**MeysamTN** - Todo list application developer

---

## 🚨 Important Notes

### Development Setup
- **Backend First**: Ensure Spring Boot backend is running on `http://localhost:8080` before starting frontend
- **Database**: PostgreSQL container must be running for full functionality
- **API Proxy**: Frontend Vite server automatically proxies API requests during development
- **Hot Reload**: Both frontend and backend support hot reloading for rapid development

### Security Considerations
- **Development Only**: Database credentials in `docker-compose.yml` are for local development
- **Production**: Use environment variables and proper secrets management for production
- **UUID Keys**: All entities use UUID for enhanced security and scalability
- **CORS**: Backend configured to accept requests from frontend origin

### Performance Features
- **Lazy Loading**: JPA entities use appropriate fetch strategies
- **Caching**: Frontend caches task data by taskListId for optimal performance
- **Optimized Builds**: Vite produces optimized, tree-shaken production bundles
- **Database Indexes**: Primary keys and foreign keys properly indexed

---

## 📁 Documentation

For detailed component-specific information:
- **Frontend Guide**: `app-ui/WARP.md` - React development, components, and state management
- **Backend Guide**: `backend/WARP.md` - Spring Boot architecture, API endpoints, and database

## 🎆 Project Status: COMPLETED

This is a fully functional, production-ready todo list application with:
- ✅ Complete frontend with modern UI
- ✅ Robust backend with REST API
- ✅ Database persistence with PostgreSQL
- ✅ Docker support for development
- ✅ Type-safe TypeScript implementation
- ✅ Comprehensive error handling
- ✅ Responsive design for all devices
