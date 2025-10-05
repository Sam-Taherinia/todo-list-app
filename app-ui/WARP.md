# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is the React TypeScript frontend for a full-stack ToDo list web application. The frontend provides a modern, responsive interface for managing ToDo lists and individual tasks, communicating with a Spring Boot backend via REST APIs. The application is fully implemented with complete CRUD functionality.

**Technology Stack:**

- React 18 with TypeScript
- Vite for build tooling and development server
- NextUI for component library
- Tailwind CSS for styling
- Framer Motion for animations
- React Router DOM for navigation
- Axios for HTTP requests
- Lucide React for icons

**UI Framework:**

- **NextUI**: Modern React UI library providing pre-built components
- **Tailwind CSS**: Utility-first CSS framework for styling
- **Framer Motion**: Animation library for smooth transitions

## Architecture

The application follows a well-structured React architecture with clear separation of concerns:

### Core Components

- **App.tsx**: Main application component with React Router configuration
- **AppProvider.tsx**: Global state management using React Context and useReducer
- **Components**: Screen-based components for different views

### Domain Models (TypeScript Interfaces/Enums)

- **Task**: Core task entity with title, description, due date, priority, and status
- **TaskList**: Container for tasks with metadata (title, description, progress tracking)
- **TaskPriority**: Enum for HIGH, MEDIUM, LOW priorities
- **TaskStatus**: Enum for OPEN, CLOSED states

### State Management Architecture

- **React Context + useReducer**: Global state management pattern
- **Action-based updates**: Dispatched actions for all state mutations
- **API integration**: Axios-based HTTP client with automatic state synchronization
- **Local state caching**: Tasks organized by taskListId for efficient access

### Component Structure

- **TaskListsScreen**: Main dashboard showing all task lists with CRUD operations
- **TasksScreen**: Individual task list view with task management
- **CreateUpdateTaskListScreen**: Form component for creating/editing task lists
- **CreateUpdateTaskScreen**: Form component for creating/editing tasks
- **App.tsx**: Main routing configuration with React Router DOM
- **AppProvider.tsx**: Global state management with Context API

### Current Implementation Status

✅ **Fully Implemented Features:**
- Complete task list CRUD (Create, Read, Update, Delete)
- Complete task CRUD within lists
- Task priority management (HIGH, MEDIUM, LOW)
- Task status tracking (OPEN, CLOSED)
- Due date functionality
- Responsive UI with NextUI components
- Global state management with automatic API synchronization
- Client-side routing with React Router DOM
- Form validation and error handling

### API Integration

- **REST API communication**: Full CRUD operations for both ToDo lists and tasks
- **Proxy configuration**: Vite dev server proxies `/api` requests to `http://localhost:8080`
- **Automatic state sync**: API calls automatically update global state via reducer actions

## Development Commands

### Setup & Installation

```powershell
# Install dependencies
npm install

# Clean installation (if needed)
npm run clean
npm install
```

### Development

```powershell
# Start development server (with HMR)
npm run dev

# Development server will start on http://localhost:5173
# API requests to /api/* will be proxied to http://localhost:8080
```

### Build & Preview

```powershell
# Build for production
npm run build

# Preview production build locally
npm run preview

# Type check without building
npx tsc --noEmit
```

### Code Quality

```powershell
# Run ESLint
npm run lint

# Fix ESLint issues automatically
npm run lint -- --fix

# Format with Prettier (if configured)
npm run format
```

### Docker Development

```powershell
# Build and run frontend in Docker container
docker-compose up --build

# Frontend will be available on http://localhost:5173
# Docker container includes host.docker.internal mapping for backend communication
```

### Testing

```powershell
# Run tests (when test framework is added)
npm test

# Run tests in watch mode
npm test -- --watch
```

## Configuration Files

- **vite.config.ts**: Vite configuration with React plugin and API proxy
- **tsconfig.json**: TypeScript compiler configuration
- **tailwind.config.js**: Tailwind CSS customization
- **eslint.config.js**: ESLint rules and configuration
- **package.json**: Dependencies and npm scripts

## Development Workflow

### Backend Integration

- **API Base URL**: The frontend expects the Spring Boot backend at `http://localhost:8080`
- **Proxy Configuration**: Vite dev server automatically proxies `/api/*` requests to the backend
- **CORS**: Backend is configured to accept requests from the frontend origin
- **Data Flow**: All state changes go through API calls that automatically update the global state

### State Management Pattern

1. **Actions**: All state changes go through dispatched actions
2. **API Calls**: Automatically trigger state updates via reducer
3. **Context**: Global state accessible via `useAppContext()` hook
4. **Local Caching**: Tasks cached by taskListId for performance

### Routing Structure

- `/` - ToDo lists dashboard
- `/new-task-list` - Create new ToDo list
- `/edit-task-list/:listId` - Edit existing ToDo list
- `/task-lists/:listId` - View tasks in a specific list
- `/task-lists/:listId/new-task` - Create new task in list
- `/task-lists/:listId/edit-task/:taskId` - Edit specific task

## Backend API Integration

The frontend integrates with these Spring Boot backend endpoints:

### Task Lists API

- `GET /task-lists` - Fetch all task lists (proxied via `/api/task-lists`)
- `GET /task-lists/{id}` - Get specific task list by UUID
- `POST /task-lists` - Create new task list
- `PUT /task-lists/{id}` - Update existing task list
- `DELETE /task-lists/{id}` - Delete task list

### Tasks API

- `GET /task-lists/{listId}/tasks` - Fetch all tasks in a specific list
- `GET /task-lists/{listId}/tasks/{taskId}` - Get specific task
- `POST /task-lists/{listId}/tasks` - Create new task in list
- `PUT /task-lists/{listId}/tasks/{taskId}` - Update existing task
- `DELETE /task-lists/{listId}/tasks/{taskId}` - Delete specific task

**Note**: All API calls are automatically proxied from `/api/*` to `http://localhost:8080/*` during development.
