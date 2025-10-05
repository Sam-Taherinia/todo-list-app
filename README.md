# ToDo List Web Application

A modern, full-stack ToDo list application built with a React TypeScript frontend and Spring Boot backend. This application allows users to create multiple task lists and manage tasks within each list with features like priority levels, due dates, and status tracking.

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

### Task Management
- ✅ Create, read, update, and delete task lists
- ✅ Create, read, update, and delete tasks within lists
- ✅ Task priorities: HIGH, MEDIUM, LOW
- ✅ Task status: OPEN, CLOSED
- ✅ Due date tracking
- ✅ Automatic timestamps (created/updated dates)

### User Interface
- 🎨 Modern, responsive design with NextUI components
- 📱 Mobile-friendly interface
- ⚡ Smooth animations and transitions
- 🔄 Real-time state synchronization
- 🎯 Intuitive navigation between lists and tasks

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

## 🗂️ Project Structure

### Frontend Structure (`app-ui/`)
```
src/
├── components/                    # React components
│   ├── TaskListsScreen.tsx       # Main dashboard
│   ├── TasksScreen.tsx           # Tasks view
│   ├── CreateUpdateTaskListScreen.tsx
│   └── CreateUpdateTaskScreen.tsx
├── domain/                       # TypeScript domain models
│   ├── Task.ts
│   ├── TaskList.ts
│   ├── TaskPriority.ts
│   └── TaskStatus.ts
├── App.tsx                       # Main app component with routing
├── AppProvider.tsx               # Global state management
└── main.tsx                      # Application entry point
```

### Backend Structure (`backend/`)
```
src/main/java/com/tromaya/todo_list/
├── controllers/                  # REST controllers
├── services/                     # Business logic layer
├── repositories/                 # Data access layer
├── domain/
│   ├── entities/                # JPA entities
│   └── dto/                     # Data transfer objects
├── mappers/                     # Entity-DTO mappers
└── TodoListApplication.java     # Spring Boot main class
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

## 🔧 Development Tips

### Database Configuration
- The backend uses PostgreSQL for production and H2 for testing
- Database connection details are configured in `application.properties`
- Use the provided `docker-compose.yml` to run PostgreSQL locally

### Frontend Development
- The Vite dev server proxies API requests to the backend automatically
- Hot module replacement (HMR) provides instant feedback during development
- TypeScript provides compile-time type checking

### State Management
- The frontend uses React Context with useReducer for global state
- All API calls automatically update the global state
- Tasks are cached by taskListId for efficient access

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

- Ensure the backend is running before starting the frontend for full functionality
- The frontend expects the backend to be available at `http://localhost:8080`
- Database credentials in `docker-compose.yml` are for development only
- Use environment variables for production database configuration

For more detailed information about each component, see the individual README files in the `app-ui/` and `backend/` directories.
